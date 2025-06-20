<template>
  <q-page class="q-pa-md">
    <h2>Category</h2>
    <div class="row q-gutter-md q-mb-md">
      <q-input outlined debounce="300" v-model="keyword" :placeholder="t('button.search')">
        <template v-slot:append>
          <q-icon name="search" />
        </template>
      </q-input>
      <q-space></q-space>
      <q-btn color="accent" icon="add" @click="openDiaglog = true" :label="t('category.create')" />
    </div>
    <q-table :rows="categories" :columns="columns" :loading="loading" :filter="keyword" :filter-method="search"
      row-key="id">
      <template v-slot:body-cell-actions="props">
        <q-td :props="props" auto-width style="min-width: 120px;">
          <q-btn icon="delete" @click="deleteCategory(props.row)" round class="q-ml-sm" text-color="grey-7" />
        </q-td>
      </template>
    </q-table>
    <q-dialog v-model="openDiaglog">
      <q-card>
        <q-card-section class="q-pt-none">
          <q-input v-model="category_request.name" :label="t('category.name')" required />
        </q-card-section>
        <q-card-actions align="right" class="text-primary">
          <q-btn flat :label="t('button.add')" @click="AddCategory" />
          <q-btn flat :label="t('button.close')" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </q-page>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import api, { Category, CategoryRequest } from '../../../services/api';
import * as ui from '../../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
const loading = ref(false)
const categories = ref<Category[]>([])
const keyword = ref('')
const openDiaglog = ref(false)
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const category_request = ref<CategoryRequest>({ name: '', businessId: userInfo.value.businessId })
const columns = [
  { name: 'name', label: t('category.name'), align: 'left' as const, field: 'name', sortable: true },
  { name: 'actions', label: t('category.action'), align: 'right' as const, field: '', sortable: false }
];

function search(rows, terms) {
  const lowerTerms = terms ? terms.toLowerCase() : ''
  return lowerTerms != '' ? rows.filter(row => row.name.includes(lowerTerms)) : categories
}
async function fetchCategory() {
  try {
    loading.value = true;
    const res = await api.api.category.getCategories(userInfo.value.businessId);
    categories.value = res;
    loading.value = false;

  } catch {
    ui.error(t('error.unknown'))
  }
}

async function deleteCategory(row) {
  try {
    await api.api.category.deleteCategory(row.id)
    await fetchCategory()
    ui.success(t('success.delete'))
  } catch {
    ui.error(t('error.unknown'))
  }

}

async function AddCategory() {
  try {
    await api.api.category.createCategory(category_request.value)
    openDiaglog.value = false
    await fetchCategory()
    ui.success(t('success.save'))
  } catch (error) {
    ui.error(t('error.unknown'))
  }

}
onMounted(async () => {
  await fetchCategory()
})
</script>