<template>
  <q-page class="q-pa-md">
      <h1>Purchase Orders</h1>
      <div class="row q-gutter-md q-mb-md">
          <q-input outlined debounce="300" v-model="keyword" placeholder="Search">
              <template v-slot:append>
                  <q-icon name="search" />
              </template>
          </q-input>
          <q-space></q-space>
          <q-btn color="accent" icon="add" to="./purchaseOrders/create" label="Create Purchase Orders" />
      </div>
      <q-table :rows="purchaseOrders" :columns="columns" :loading="loading" :filter="keyword" :filter-method="search"
          row-key="id">
          <template v-slot:body-cell-actions="props">
              <q-td :props="props" auto-width style="min-width: 120px;">
                  <q-btn icon="edit" :to="'./purchaseOrders/' + props.row.id + '/edit'" round text-color="grey-7" />
                  <!-- <q-btn icon="delete" @click="deleteRole(props.row)" round class="q-ml-sm" text-color="grey-7"/> -->
              </q-td>
          </template>
      </q-table>
  </q-page>
</template>
<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import api, { PurchaseOrder } from '../../../services/api';
const loading = ref(false)
const purchaseOrders = ref<PurchaseOrder[]>([])
const keyword = ref('')
const columns = [
  { name: 'id', label: 'ID', align: 'left' as const, field: 'id', sortable: true },
  { name: 'totalAmount', label: 'Total Amount', align: 'left' as const, field: 'totalAmount', sortable: true },
  { name: 'totalAmountPaid', label: 'Total Amount Paid', align: 'left' as const, field: 'totalAmountPaid', sortable: true },
  { name: 'subStatus', label: 'Sub Status', align: 'left' as const, field: 'subStatus', sortable: true },
  { name: 'status', label: 'Status', align: 'left' as const, field: 'status', sortable: true },
  { name: 'actions', label: 'Actions', align: 'right' as const, field: '', sortable: false }
];

function search(rows, terms) {
  const lowerTerms = terms ? terms.toLowerCase() : ""
  return lowerTerms != "" ? rows.filter(row => row.name.includes(lowerTerms)) : purchaseOrders
}
async function fetchPurchaseOrders() {
  loading.value = true;
  const res = await api.api.purchaseOrder.getPurchaseOrders();
  purchaseOrders.value = res;
  loading.value = false;
}
onMounted(async () => {
  await fetchPurchaseOrders()
})
</script>