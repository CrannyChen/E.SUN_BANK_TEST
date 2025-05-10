<template>
    <div class="seat-container">
        <div v-for="(group, floor) in groupedSeats" :key="floor" class="floor-group">
            <div class="seat-row">
                <button
                    v-for="seat in group"
                    :key="seat.floorSeatSeq"
                    class="btn seat-btn"
                    :class="seatColor(seat)"
                    :title="getButtonTitle(seat)"
                    :disabled="isSeatDisabled(seat)"
                    @click="selectSeat(seat)"
                >
                    {{ seat.floorNo }}樓：座位{{ seat.seatNo }}
                    <span v-if="occupiedMap[seat.floorSeatSeq]">
                        [員編：{{ occupiedMap[seat.floorSeatSeq] }}]
                    </span>
                </button>
            </div>
        </div>
    </div>
</template>

<script>
import Swal from "sweetalert2";

export default {
    name: "SeatGrid",
    props: {
        selectedEmpId: String,
        currentEmpSeatSeq: Number,
        occupiedMap: Object,
        resetTrigger: Number,
    },
    data() {
        return {
            seats: [],
            selectedSeatId: null,
        };
    },
    mounted() {
        fetch("http://localhost:8080/api/seats/active")
            .then((res) => res.json())
            .then((data) => {
                this.seats = data;
            });
    },
    watch: {
        resetTrigger() {
            this.selectedSeatId = null;
        },
    },
    computed: {
        groupedSeats() {
            const map = {};
            this.seats.forEach((seat) => {
                if (!map[seat.floorNo]) map[seat.floorNo] = [];
                map[seat.floorNo].push(seat);
            });
            return map;
        },
    },
    methods: {
        seatColor(seat) {
            if (this.selectedSeatId === seat.floorSeatSeq) return "btn-success";
            if (this.occupiedMap[seat.floorSeatSeq]) return "btn-danger";
            return "btn-secondary";
        },
        getButtonTitle(seat) {
            const isOccupied = !!this.occupiedMap[seat.floorSeatSeq];
            if (!this.selectedEmpId && !isOccupied) {
                return "請先選擇要安排的員工";
            }
            return "";
        },
        isSeatDisabled(seat) {
            const isOccupied = !!this.occupiedMap[seat.floorSeatSeq];
            return !this.selectedEmpId && !isOccupied;
        },
        selectSeat(seat) {
            const seatId = seat.floorSeatSeq;
            const occupiedBy = this.occupiedMap[seatId];

            if (this.currentEmpSeatSeq && seatId === this.currentEmpSeatSeq) {
                Swal.fire("這是該員工原本的座位", "", "info");
                return;
            }

            if (occupiedBy && occupiedBy !== this.selectedEmpId) {
                Swal.fire({
                    title: `此座位已由員工 ${occupiedBy} 使用`,
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonText: "清除座位",
                    cancelButtonText: "關閉",
                }).then((result) => {
                    if (result.isConfirmed) {
                        fetch(`http://localhost:8080/api/employees/clear-seat?seatId=${seatId}`, {
                            method: "POST",
                        })
                            .then(() => {
                                Swal.fire("已成功清除座位", "", "success");
                                this.$emit("seat-cleared");
                            })
                            .catch((err) => {
                                console.error(err);
                                Swal.fire("清除座位失敗", "", "error");
                            });
                    }
                });
                return;
            }

            this.selectedSeatId = this.selectedSeatId === seatId ? null : seatId;
            this.$emit("seat-selected", this.selectedSeatId);
        },
    },
};
</script>
