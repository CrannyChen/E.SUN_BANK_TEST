<template>
    <div class="mb-3">
        <label for="employeeSelect" class="form-label">選擇員工：</label>
        <select id="employeeSelect" class="form-select" v-model="selectedId" required @change="emitSelection">
            <option value="" hidden>請選擇員工</option>
            <option v-for="emp in employees" :key="emp.empId" :value="emp.empId">
                {{ emp.empId }} - {{ emp.name }}
            </option>
        </select>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "EmployeeSelector",
    props: {
        resetTrigger: Number,
    },
    data() {
        return {
            employees: [],
            selectedId: "",
        };
    },
    mounted() {
        axios
            .get("http://localhost:8080/api/employees")
            .then((response) => {
                this.employees = response.data.filter((emp) => emp.isActive);
            })
            .catch((error) => {
                console.error("搜尋員工失敗：", error);
            });
    },
    methods: {
        emitSelection() {
            this.$emit("employee-selected", this.selectedId);
        },
    },
    watch: {
        resetTrigger() {
            this.selectedId = "";
        },
    },
};
</script>
