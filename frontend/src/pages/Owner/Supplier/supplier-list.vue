<template>
    <q-page class="q-pa-md">
        <h2>{{ t('supplier.title') }}</h2>
        <div class="row q-gutter-md q-mb-md">
            <q-input outlined debounce="300" v-model="keyword" :placeholder="t('button.search')">
                <template v-slot:append>
                    <q-icon name="search" />
                </template>
            </q-input>
            <q-space></q-space>
            <q-btn color="accent" icon="add" to="./suppliers/create" :label="t('supplier.create')" />
        </div>
        <q-table :rows="suppliers" :columns="columns" :loading="loading" :filter="keyword" :filter-method="search"
            row-key="id">
            <template v-slot:body-cell-actions="props">
                <q-td :props="props" auto-width style="min-width: 120px;">
                    <q-btn icon="edit" :to="'./suppliers/' + props.row.id + '/edit'" round text-color="grey-7" />
                    <q-btn icon="delete" @click="deleteSupplier(props.row)" round class="q-ml-sm" text-color="grey-7" />
                </q-td>
            </template>
        </q-table>
    </q-page>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import api, { Supplier } from '../../../services/api';
import * as ui from '../../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const loading = ref(false)
const suppliers = ref<Supplier[]>([])
const keyword = ref('')
const columns = [
    { name: 'phone', label: t('supplier.phone'), align: 'left' as const, field: 'phone', sortable: true },
    { name: 'name', label: t('supplier.name'), align: 'left' as const, field: 'name', sortable: true },
    { name: 'address', label: t('supplier.address'), align: 'left' as const, field: 'address', sortable: true },
    { name: 'email', label: t('supplier.address'), align: 'left' as const, field: 'email', sortable: true },
    { name: 'note', label: t('supplier.note'), align: 'left' as const, field: 'note', sortable: true },
    { name: 'branch', label: t('supplier.branch'), align: 'left' as const, field: 'branch', sortable: true },
    { name: 'mst', label: t('supplier.tax_code'), align: 'left' as const, field: 'mst', sortable: true },
    { name: 'actions', label: t('supplier.action'), align: 'right' as const, field: '', sortable: false }
];

function search(rows, terms) {
    const lowerTerms = terms ? terms.toLowerCase() : ''
    return lowerTerms != '' ? rows.filter(row => row.name.includes(lowerTerms)) : suppliers
}
async function fetchSuppliers() {
    try {
        loading.value = true;
        const res = await api.api.supplier.getSuppiers(userInfo.value.businessId);
        suppliers.value = res;
        loading.value = false;
    } catch {
        ui.error(t('error.unknown'))
    }
}

async function deleteSupplier(supplier) {
    try {
        loading.value = true;
        await api.api.supplier.deleteSupplier(supplier.id);
        fetchSuppliers();
        loading.value = false;
        ui.success('delete sucessfull')
    } catch {
        ui.error(t('error.unknown'))
    }
}
onMounted(async () => {
    await fetchSuppliers()
})
</script>
