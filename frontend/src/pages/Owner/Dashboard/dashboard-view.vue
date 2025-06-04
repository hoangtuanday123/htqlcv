<template>
    <QcardDashboard />
    <q-card class="q-mt-sm no-shadow" bordered>
        <q-card-section class="text-h6 q-pb-none">
            <q-item>
                <q-item-section avatar class="">
                    <q-icon color="blue" name="sell" size="44px" />
                </q-item-section>

                <q-item-section>
                    <div class="text-h6">{{ t('dashboard.product_sale_title') }}</div>
                </q-item-section>
            </q-item>
        </q-card-section>
        <q-card-section class="row">
            <div class="col-12">
                <Bar id="my-chart-id" :options="chartOptions" :data="chartData" class="q-mt-md"
                    style="width: 100%; max-height: 600px;" autoresize />
            </div>
        </q-card-section>
    </q-card>
</template>

<script setup lang="ts">
import QcardDashboard from 'src/components/QcardDashboard.vue';
import { onMounted, ref } from 'vue';
import api, { RevenueByProductAndMonth } from '../../../services/api';
import * as ui from '../../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import { useCurrentuser } from '../../../share/currentuser';
const loading = ref(false)
const currentUser = useCurrentuser()
const userInfo = currentUser.info
import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)
const chartData = ref({
    labels: [
        'Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4',
        'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8',
        'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'
    ],
    datasets: []
});

const chartOptions = {
    responsive: true,
    maintainAspectRatio: false, // Cho phép điều chỉnh tự do chiều cao
    plugins: {
        title: {
            display: true,
            text: `${t('dashboard.product_sale_body')} ${new Date().getFullYear()}`
        }
    },
    scales: {
        x: {
            ticks: {
                maxRotation: 0,
                minRotation: 0
            }
        }
    }
};
function getRandomBackgroundColor() {
    const r = Math.floor(Math.random() * 256);  // 0-255
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);
    const a = 0.6;
    return `rgba(${r}, ${g}, ${b}, ${a})`;
}

async function fetch() {
    try {
        loading.value = true
        const res = await api.api.dashboard.getRevenue(String(userInfo.value.businessId)) as RevenueByProductAndMonth[];

        // Nhóm dữ liệu theo productName
        const groupedData: Record<string, RevenueByProductAndMonth[]> = {};

        for (const item of res) {
            if (!groupedData[item.productName]) {
                groupedData[item.productName] = [];
            }
            groupedData[item.productName].push(item);
        }

        const datasets = [];

        for (const [productName, items] of Object.entries(groupedData)) {
            const revenueByMonth = [];

            for (let month = 1; month <= 12; month++) {
                const monthData = items.find(i => i.month === month);
                revenueByMonth.push(monthData ? monthData.totalRevenue : 0);
            }

            datasets.push({
                label: productName,
                data: revenueByMonth,
                backgroundColor: getRandomBackgroundColor()
            });
        }

        chartData.value = {
            ...chartData.value,
            datasets: datasets
        }
        loading.value = false;
    } catch (e) {
        ui.error(t('error.unknown'));
        loading.value = false;
    }
}




onMounted(async () => {
    await fetch()
})
</script>