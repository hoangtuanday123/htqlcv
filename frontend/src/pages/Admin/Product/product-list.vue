<template>
    <q-page class="q-pa-md">
        <h1>Products</h1>
        <div class="row q-gutter-md q-mb-md">
            <q-input outlined debounce="300" v-model="keyword" placeholder="Search">
                <template v-slot:append>
                    <q-icon name="search" />
                </template>
            </q-input>
            <q-space></q-space>
            <q-btn color="accent" icon="add" to="./products/create" label="Create Product" />
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
import { computed, onMounted, ref } from 'vue';
import api, { Product } from '../../../services/api';
const loading = ref(false)
const products = ref<Product[]>([])
const keyword = ref('')
const columns = [
    { name: 'name', label: 'Name', align: 'left' as const, field: 'name', sortable: true },
    { name: 'capitalPrice', label: 'Capital Price', align: 'left' as const, field: 'capitalPrice', sortable: true },
    { name: 'salePrice', label: 'Sale Price', align: 'left' as const, field: 'salePrice', sortable: true },
    { name: 'stockQuantity', label: 'Stock Quantity', align: 'left' as const, field: 'stockQuantity', sortable: true },
    { name: 'actions', label: 'Actions', align: 'right' as const, field: '', sortable: false }
];

function search(rows, terms) {
    const lowerTerms = terms ? terms.toLowerCase() : ""
    return lowerTerms != "" ? rows.filter(row => row.name.includes(lowerTerms)) : products
}
async function fetchProducts() {
    loading.value = true;
    const res = await api.api.product.getProducts();
    console.log(res)
    products.value = res;
    loading.value = false;
}

async function deleteProduct(product) {
    loading.value = true;
    await api.api.product.deleteProduct(product.id);
    fetchProducts();
    loading.value = false;
}
onMounted(async () => {
    await fetchProducts()
})
</script>