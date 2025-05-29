<template>
  <q-page class="q-pa-md">
      <h1>Branch Product</h1>
      <div class="row q-gutter-md q-mb-md">
          <q-input outlined debounce="300" v-model="keyword" placeholder="Search">
              <template v-slot:append>
                  <q-icon name="search" />
              </template>
          </q-input>
          <q-space></q-space>
          <q-btn color="accent" icon="add" @click="openDiaglog=true"  label="Create Branch Product" />
      </div>
      <q-table :rows="branchProducts" :columns="columns" :loading="loading" :filter="keyword" :filter-method="search"
          row-key="id">
          <template v-slot:body-cell-actions="props">
              <q-td :props="props" auto-width style="min-width: 120px;">
                  <q-btn icon="delete" @click="deleteBranchProduct(props.row)" round class="q-ml-sm" text-color="grey-7"/>
              </q-td>
          </template>
      </q-table>
      <q-dialog v-model="openDiaglog">
        <q-card>
          <q-card-section class="q-pt-none">
            <q-input v-model="branchProduct_request.name" label="Name" required />
          </q-card-section>
          <q-card-actions align="right" class="text-primary">
                <q-btn flat label="Add" @click="AddBranchProduct" />
                <q-btn flat label="Close" v-close-popup />
            </q-card-actions>
        </q-card>
      </q-dialog>
  </q-page>
</template>
<script setup lang="ts">
import {  onMounted, ref } from 'vue';
import api, { BranchProduct,BranchProductRequest } from '../../../services/api';
const loading = ref(false)
const branchProducts = ref<BranchProduct[]>([])
const keyword = ref('')
const openDiaglog=ref(false)
const branchProduct_request=ref<BranchProductRequest>({name:''})
const columns = [
  { name: 'name', label: 'Name', align: 'left' as const, field: 'name', sortable: true },
  { name: 'actions', label: 'Actions', align: 'right' as const, field: '', sortable: false }
];

function search(rows, terms) {
  const lowerTerms = terms ? terms.toLowerCase() : ""
  return lowerTerms != "" ? rows.filter(row => row.name.includes(lowerTerms)) : branchProducts
}
async function fetchBranchProducts() {
  loading.value = true;
  const res = await api.api.branchProduct.getBranchProducts();
  branchProducts.value = res;
  loading.value = false;
}

async function deleteBranchProduct(row) {
    await api.api.branchProduct.deleteBranchProduct(row.id)
    await fetchBranchProducts()
}

async function AddBranchProduct() {
    await api.api.branchProduct.createBranchProduct(branchProduct_request.value)
    openDiaglog.value=false
    await fetchBranchProducts()
}
onMounted(async () => {
  await fetchBranchProducts()
})
</script>