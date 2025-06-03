<template>
    <q-card class="bg-transparent no-shadow no-border" bordered>
        <q-card-section class="q-pa-none">
            <div class="row q-col-gutter-sm ">
                <div v-for="(item, index) in items" :key="index" class="col-md-3 col-sm-12 col-xs-12">
                    <q-item :style="`background-color: ${item.color1}`" class="q-pa-none">
                        <q-item-section v-if="icon_position === 'left'" side :style="`background-color: ${item.color2}`"
                            class=" q-pa-lg q-mr-none text-white">
                            <q-icon :name="item.icon" color="white" size="24px"></q-icon>
                        </q-item-section>
                        <q-item-section class=" q-pa-md q-ml-none  text-white">
                            <q-item-label class="text-white text-h6 text-weight-bolder">{{ item.value }}</q-item-label>
                            <q-item-label>{{ item.title }}</q-item-label>
                        </q-item-section>
                        <q-item-section v-if="icon_position === 'right'" side class="q-mr-md text-white">
                            <q-icon :name="item.icon" color="white" size="44px"></q-icon>
                        </q-item-section>
                    </q-item>
                </div>
            </div>
        </q-card-section>
    </q-card>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import api, { DashBoard } from '../services/api';
import * as ui from '../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import { useCurrentuser } from '../share/currentuser';
const loading = ref(false)
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const dashboard = ref<DashBoard>({
    totalCustomer: 0,
    totalStaff: 0,
    totalSupplier: 0,
    totalAmoundThisMonth: 0,
    totalAmoundPurchaseThisMonth: 0,
    totalProfit: 0,
    stockQuantity: 0
})
type DashboardItem = {
    title: string
    icon: string
    value: number | string
    color1: string
    color2: string
}

const items = ref<DashboardItem[]>([])
const icon_position = ref('right')
function formatCurrency(value: number): string {
    return value.toLocaleString('vi-VN') + ' ₫'
}
async function fetch() {
    try {
        loading.value = true
        const res = await api.api.dashboard.getDashboard(String(userInfo.value.businessId))
        console.log(res)
        dashboard.value = res
        console.log(dashboard.value.totalAmoundThisMonth)
        items.value.push({
            title: 'My Customer',
            icon: 'person',
            value: dashboard.value.totalCustomer,
            color1: '#5064b5',
            color2: '#3e51b5'
        })
        items.value.push({
            title: 'My Staff',
            icon: 'person',
            value: dashboard.value.totalStaff - 1,
            color1: '#f37169',
            color2: '#f34636'
        })
        items.value.push({
            title: 'My Supplier',
            icon: 'person',
            value: dashboard.value.totalSupplier,
            color1: '#ea6a7f',
            color2: '#ea4b64'
        })
        items.value.push({
            title: 'Monthly Income',
            icon: 'money',
            value: formatCurrency(dashboard.value.totalAmoundThisMonth),
            color1: '#a270b1',
            color2: '#9f52b1'
        })
        items.value.push({
            title: 'Monthly Profit',
            icon: 'trending_up',
            value: formatCurrency(dashboard.value.totalProfit),
            color1: '#546bfa',
            color2: '#3e51b5'
        })
        items.value.push({
            title: 'Monthly purchase capital',
            icon: 'money',
            value: formatCurrency(dashboard.value.totalAmoundPurchaseThisMonth),
            color1: '#3a9688',
            color2: '#3e51b5'
        })
        items.value.push({
            title: 'Stock Quantity',
            icon: 'shopping_bag',
            value: dashboard.value.stockQuantity,
            color1: '#7cb342',
            color2: '#3e51b5'
        })
        loading.value = false
    }
    catch {
        ui.error(t('error.unknown'))
    }
}
onMounted(async () => {
    await fetch()
})
</script>