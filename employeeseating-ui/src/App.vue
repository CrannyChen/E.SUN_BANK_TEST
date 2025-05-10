<template>
    <div class="container mt-4">
        <div class="layout-aligned">
            <h2 class="mb-3 text-dark">人資部門-員工座位安排系統</h2>

            <EmployeeSelector :resetTrigger="resetSelectKey" @employee-selected="onEmpSelected" />

            <div class="alert alert-info p-3">
                <p class="mb-1">
                    <strong>目前選擇員工：</strong>
                    <span v-if="selectedEmpName">{{ selectedEmpId }} - {{ selectedEmpName }}</span>
                    <span v-else class="text-muted">尚未選擇</span>
                </p>
                <p class="mb-0">
                    <strong>目前選擇座位：</strong>
                    <span v-if="selectedSeatInfo">{{ selectedSeatInfo }}</span>
                    <span v-else class="text-muted">尚未選擇</span>
                </p>
            </div>
        </div>

        <SeatGrid
            :selectedEmpId="selectedEmpId"
            :currentEmpSeatSeq="currentEmpSeatSeq"
            :occupiedMap="occupiedMap"
            :resetTrigger="resetSeatKey"
            @seat-selected="onSeatSelected"
            @seat-cleared="refreshAfterClear"
        />

        <div class="layout-aligned seat-legend-container-spaced text-dark">
            <div class="seat-stats mb-4">
                <strong>座位統計：</strong>
                共 {{ seatStats.total }} 席， 已佔用 {{ seatStats.occupied }} 席， 空位
                {{ seatStats.available }} 席
            </div>
            <div class="d-flex flex-column gap-3">
                <div class="d-flex align-items-center gap-2">
                    <span class="legend-box bg-secondary"></span>
                    <span class="legend-label">空位</span>
                </div>
                <div class="d-flex align-items-center gap-2">
                    <span class="legend-box bg-danger"></span>
                    <span class="legend-label">已佔用</span>
                </div>
                <div class="d-flex align-items-center gap-2">
                    <span class="legend-box bg-success-custom"></span>
                    <span class="legend-label">請選擇</span>
                </div>
            </div>
        </div>

        <div class="layout-aligned custom-button-spacing">
            <button
                :class="[
                    'btn',
                    'no-radius',
                    selectedEmpId && selectedSeatId !== null ? 'btn-primary' : 'btn-disabled',
                ]"
                :disabled="!selectedEmpId || selectedSeatId === null"
                @click="assignSeat"
            >
                送出
            </button>
        </div>
    </div>
</template>

<script>
import EmployeeSelector from "./components/EmployeeSelector.vue";
import SeatGrid from "./components/SeatGrid.vue";
import axios from "axios";
import Swal from "sweetalert2";

export default {
    name: "App",
    components: { EmployeeSelector, SeatGrid },
    data() {
        return {
            selectedEmpId: "",
            selectedEmpName: "",
            selectedSeatId: null,
            selectedSeatInfo: "",
            currentEmpSeatSeq: null,
            occupiedMap: {},
            seatStats: { total: 0, occupied: 0, available: 0 },
            seats: [],
            resetSelectKey: 0,
            resetSeatKey: 0,
        };
    },
    created() {
        this.loadOccupiedMap();
        this.loadSeats();
    },
    methods: {
        onEmpSelected(empId) {
            this.selectedEmpId = empId;
            this.selectedSeatId = null;
            this.selectedEmpName = "";
            this.selectedSeatInfo = "";
            this.resetSeatKey++;
            this.loadCurrentSeat(empId);
        },
        onSeatSelected(seatId) {
            this.selectedSeatId = seatId;
            const seat = this.seats.find((s) => s.floorSeatSeq === seatId);
            this.selectedSeatInfo = seat ? `${seat.floorNo}樓 座位 ${seat.seatNo}` : "";
        },
        assignSeat() {
            if (!this.selectedEmpId || this.selectedSeatId === null) return;
            axios
                .post("http://localhost:8080/api/employees/assign-seat", null, {
                    params: { empId: this.selectedEmpId, seatId: this.selectedSeatId },
                })
                .then(() => {
                    Swal.fire("安排座位成功", "", "success");
                    this.selectedEmpId = "";
                    this.selectedEmpName = "";
                    this.selectedSeatId = null;
                    this.selectedSeatInfo = "";
                    this.currentEmpSeatSeq = null;
                    this.resetSelectKey++;
                    this.resetSeatKey++;
                    this.refreshAfterClear();
                })
                .catch((err) => {
                    console.error(err);
                    Swal.fire("安排失敗", err.message || "請稍後再試", "error");
                });
        },
        refreshAfterClear() {
            this.loadOccupiedMap();
            this.loadSeats();
        },
        loadCurrentSeat(empId) {
            axios.get("http://localhost:8080/api/employees").then((res) => {
                const emp = res.data.find((e) => e.empId === empId);
                this.currentEmpSeatSeq = emp?.seatingChart?.floorSeatSeq ?? null;
                this.selectedEmpName = emp?.name ?? "";
            });
        },
        loadOccupiedMap() {
            axios.get("http://localhost:8080/api/employees").then((res) => {
                this.occupiedMap = {};
                res.data.forEach((e) => {
                    if (e.seatingChart && e.isActive) {
                        this.occupiedMap[e.seatingChart.floorSeatSeq] = e.empId;
                    }
                });
                this.updateStats();
            });
        },
        loadSeats() {
            axios.get("http://localhost:8080/api/seats/active").then((res) => {
                this.seats = res.data;
                this.updateStats();
            });
        },
        updateStats() {
            const total = this.seats.length;
            const occupied = Object.keys(this.occupiedMap).length;
            const available = total - occupied;
            this.seatStats = { total, occupied, available };
        },
    },
};
</script>
