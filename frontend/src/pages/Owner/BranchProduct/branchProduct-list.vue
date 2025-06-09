<template>
  <q-page class="q-pa-md">
    <h2>{{ t('branch_product.title') }}</h2>
    <div class="row q-gutter-md q-mb-md">
      <q-input outlined debounce="300" v-model="keyword" :placeholder="t('button.search')">
        <template v-slot:append>
          <q-icon name="search" />
        </template>
      </q-input>
      <q-space></q-space>
      <q-btn color="accent" icon="add" @click="openDiaglog = true" :label="t('branch_product.create')" />
    </div>
    <q-table :rows="branchProducts" :columns="columns" :loading="loading" :filter="keyword" :filter-method="search"
      row-key="id">
      <template v-slot:body-cell-actions="props">
        <q-td :props="props" auto-width style="min-width: 120px;">
          <q-btn icon="delete" @click="deleteBranchProduct(props.row)" round class="q-ml-sm" text-color="grey-7" />
        </q-td>
      </template>
    </q-table>
    <q-dialog v-model="openDiaglog">
      <q-card>
        <q-card-section class="q-pt-none">
          <q-input v-model="branchProduct_request.name" :label="t('branch_product.name')" required />
        </q-card-section>
        <q-card-actions align="right" class="text-primary">
          <q-btn flat :label="t('button.add')" @click="AddBranchProduct" />
          <q-btn flat :label="t('button.close')" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </q-page>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import api, { BranchProduct, BranchProductRequest } from '../../../services/api';
import * as ui from '../../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
const loading = ref(false)
const branchProducts = ref<BranchProduct[]>([])
const keyword = ref('')
const openDiaglog = ref(false)
import { useCurrentuser } from '../../../share/currentuser';

const currentUser = useCurrentuser()
const userInfo = currentUser.info
const branchProduct_request = ref<BranchProductRequest>({ name: '', businessId: userInfo.value.businessId })
const columns = [
  { name: 'name', label: t('branch_product.name'), align: 'left' as const, field: 'name', sortable: true },
  { name: 'actions', label: t('branch_product.action'), align: 'right' as const, field: '', sortable: false }
];

function search(rows, terms) {
  const lowerTerms = terms ? terms.toLowerCase() : ''
  return lowerTerms != '' ? rows.filter(row => row.name.includes(lowerTerms)) : branchProducts
}
async function fetchBranchProducts() {
  try {
    loading.value = true;
    const res = await api.api.branchProduct.getBranchProducts(String(userInfo.value.businessId));
    branchProducts.value = res;
    loading.value = false;
  }
  catch {
    ui.error(t('error.unknown'))
  }

}

async function deleteBranchProduct(row) {
  try {
    await api.api.branchProduct.deleteBranchProduct(row.id)
    await fetchBranchProducts()
    ui.success(t('success.delete'))
  }
  catch {
    ui.error(t('error.unknown'))
  }
}

async function AddBranchProduct() {
  try {
    await api.api.branchProduct.createBranchProduct(branchProduct_request.value)
    openDiaglog.value = false
    await fetchBranchProducts()
    ui.success(t('success.save'))
  } catch {
    ui.error(t('error.unknown'))
  }
}
onMounted(async () => {
  await fetchBranchProducts()
})
</script>