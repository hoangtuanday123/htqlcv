<template>
  <q-page class="q-pa-md">
    <h2>{{ t('sale_order.title') }}</h2>
    <div class="row q-gutter-md q-mb-md">
      <q-input outlined debounce="300" v-model="keyword" :placeholder="t('button.search')">
        <template v-slot:append>
          <q-icon name="search" />
        </template>
      </q-input>
      <q-space></q-space>
      <q-btn color="accent" icon="add" to="./saleOrders/create" :label="t('sale_order.create')" />
    </div>
    <q-table :rows="SaleOrders" :columns="columns" :loading="loading" :filter="keyword" :filter-method="search"
      row-key="id">
      <template v-slot:body-cell-dept="props">
        <q-td :props="props">
          {{ props.row.totalAmount - props.row.totalAmountPaid }}
        </q-td>
      </template>
      <template v-slot:body-cell-actions="props">
        <q-td :props="props" auto-width style="min-width: 120px;">
          <q-btn icon="edit" :to="'./saleOrders/' + props.row.id + '/edit'" round text-color="grey-7" />
          <q-btn icon="delete" @click="deletesaleOrder(props.row)" round class="q-ml-sm" text-color="grey-7"
            v-if="!['Completed', 'Cancelled', 'Refunded'].includes(props.row.status)" />
        </q-td>
      </template>
    </q-table>
  </q-page>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import api, { SaleOrder } from '../../../services/api';
import * as ui from '../../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const loading = ref(false)
const SaleOrders = ref<SaleOrder[]>([])
const keyword = ref('')
const columns = [
  { name: 'id', label: 'ID', align: 'left' as const, field: 'id', sortable: true },
  { name: 'totalAmount', label: t('sale_order.total_amound'), align: 'left' as const, field: 'totalAmount', sortable: true },
  { name: 'totalAmountPaid', label: t('sale_order.total_amound_paid'), align: 'left' as const, field: 'totalAmountPaid', sortable: true },
  { name: 'dept', label: t('sale_order.dept'), align: 'left' as const, field: 'dept', sortable: true },
  { name: 'subStatus', label: t('sale_order.sub_status'), align: 'left' as const, field: 'subStatus', sortable: true },
  { name: 'status', label: t('sale_order.status'), align: 'left' as const, field: 'status', sortable: true },
  { name: 'actions', label: t('sale_order.action'), align: 'right' as const, field: '', sortable: false }
];

function search(rows, terms) {
  const lowerTerms = terms ? terms.toLowerCase() : ''
  return lowerTerms != '' ? rows.filter(row => row.name.includes(lowerTerms)) : SaleOrders
}
async function fetchSaleOrders() {
  try {
    loading.value = true;
    const res = await api.api.saleOrder.getSaleOrders(String(userInfo.value.businessId));
    SaleOrders.value = res;
    loading.value = false;
  } catch {
    ui.error(t('error.unknown'))
  }
}
async function deletesaleOrder(saleOrder) {
  try {
    loading.value = true;
    await api.api.saleOrder.deleteSaleOrder(saleOrder.id);
    fetchSaleOrders();
    loading.value = false;
    ui.success('delete sucessfull')
  } catch {
    ui.error(t('error.unknown'))
  }
}
onMounted(async () => {
  await fetchSaleOrders()
})
</script>