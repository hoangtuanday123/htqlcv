<template>
  <q-page class="q-pa-md">
    <h2>{{ t('purchase_order.title') }}</h2>
    <div class="row q-gutter-md q-mb-md">
      <q-input outlined debounce="300" v-model="keyword" :placeholder="t('button.search')">
        <template v-slot:append>
          <q-icon name="search" />
        </template>
      </q-input>
      <q-space></q-space>
      <q-btn color="accent" icon="add" to="./purchaseOrders/create" :label="t('purchase_order.create')" />
    </div>
    <q-table :rows="purchaseOrders" :columns="columns" :loading="loading" :filter="keyword" :filter-method="search"
      row-key="id">
      <template v-slot:body-cell-dept="props">
        <q-td :props="props">
          {{ props.row.totalAmount - props.row.totalAmountPaid }}
        </q-td>
      </template>
      <template v-slot:body-cell-actions="props">
        <q-td :props="props" auto-width style="min-width: 120px;">
          <q-btn icon="edit" :to="'./purchaseOrders/' + props.row.id + '/edit'" round text-color="grey-7" />
          <q-btn icon="delete" @click="deletepurchaseOrder(props.row)" round class="q-ml-sm" text-color="grey-7"
            v-if="!['Completed', 'Cancelled'].includes(props.row.status)" />
        </q-td>
      </template>
    </q-table>
  </q-page>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import api, { PurchaseOrder } from '../../../services/api';
import * as ui from '../../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const loading = ref(false)
const purchaseOrders = ref<PurchaseOrder[]>([])
const keyword = ref('')
const columns = [
  { name: 'id', label: 'ID', align: 'left' as const, field: 'id', sortable: true },
  { name: 'totalAmount', label: t('purchase_order.total_amound'), align: 'left' as const, field: 'totalAmount', sortable: true },
  { name: 'totalAmountPaid', label: t('purchase_order.total_amound_paid'), align: 'left' as const, field: 'totalAmountPaid', sortable: true },
  { name: 'dept', label: t('purchase_order.dept'), align: 'left' as const, field: 'dept', sortable: true },
  { name: 'subStatus', label: t('purchase_order.sub_status'), align: 'left' as const, field: 'subStatus', sortable: true },
  { name: 'status', label: t('purchase_order.status'), align: 'left' as const, field: 'status', sortable: true },
  { name: 'actions', label: t('purchase_order.action'), align: 'right' as const, field: '', sortable: false }
];

function search(rows, terms) {
  const lowerTerms = terms ? terms.toLowerCase() : ''
  return lowerTerms != '' ? rows.filter(row => row.name.includes(lowerTerms)) : purchaseOrders
}
async function fetchPurchaseOrders() {
  try {
    loading.value = true;
    const res = await api.api.purchaseOrder.getPurchaseOrders(String(userInfo.value.businessId));
    purchaseOrders.value = res;
    loading.value = false;
  } catch {
    ui.error(t('error.unknown'))
  }
}
async function deletepurchaseOrder(purchaseOrder) {
  try {
    loading.value = true;
    await api.api.purchaseOrder.deletePurchaseOrder(purchaseOrder.id);
    fetchPurchaseOrders();
    loading.value = false;
    ui.success('delete sucessfull')
  } catch {
    ui.error(t('error.unknown'))
  }
}
onMounted(async () => {
  await fetchPurchaseOrders()
})
</script>