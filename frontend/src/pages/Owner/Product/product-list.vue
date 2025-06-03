<template>
    <q-page class="q-pa-md">
        <h1>{{ t('product.title') }}</h1>
        <div class="row q-gutter-md q-mb-md">
            <q-input outlined debounce="300" v-model="keyword" :placeholder="t('button.search')">
                <template v-slot:append>
                    <q-icon name="search" />
                </template>
            </q-input>
            <q-space></q-space>
            <q-btn color="accent" icon="add" to="./products/create" :label="t('product.create')" />
        </div>
        <q-table :rows="products" :columns="columns" :loading="loading" :filter="keyword" :filter-method="search"
            row-key="id">
            <template v-slot:body-cell-actions="props">
                <q-td :props="props" auto-width style="min-width: 120px;">
                    <q-btn icon="edit" :to="'./products/' + props.row.id + '/edit'" round text-color="grey-7" />
                    <q-btn icon="delete" @click="deleteProduct(props.row)" round class="q-ml-sm" text-color="grey-7" />
                </q-td>
            </template>
        </q-table>
    </q-page>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import api, { Product } from '../../../services/api';
import * as ui from '../../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const loading = ref(false)
const products = ref<Product[]>([])
const keyword = ref('')
const columns = [
    { name: 'name', label: t('product.name'), align: 'left' as const, field: 'name', sortable: true },
    { name: 'capitalPrice', label: t('product.capital_price'), align: 'left' as const, field: 'capitalPrice', sortable: true },
    { name: 'salePrice', label: t('product.sale_price'), align: 'left' as const, field: 'salePrice', sortable: true },
    { name: 'stockQuantity', label: t('product.stock_quantity'), align: 'left' as const, field: 'stockQuantity', sortable: true },
    { name: 'actions', label: t('product.action'), align: 'right' as const, field: '', sortable: false }
];

function search(rows, terms) {
    const lowerTerms = terms ? terms.toLowerCase() : ''
    return lowerTerms != '' ? rows.filter(row => row.name.includes(lowerTerms)) : products
}
async function fetchProducts() {
    try {
        loading.value = true;
        const res = await api.api.product.getProducts(String(userInfo.value.businessId));
        console.log(res)
        products.value = res;
        loading.value = false;
    }
    catch {
        ui.error(t('error.unknown'))
    }
}

async function deleteProduct(product) {
    try {
        loading.value = true;
        await api.api.product.deleteProduct(product.id);
        await fetchProducts();
        loading.value = false;
        ui.success('delete sucessfull')
    }
    catch {
        ui.error(t('error.unknown'))
    }
}
onMounted(async () => {
    await fetchProducts()
})
</script>