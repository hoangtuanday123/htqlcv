<template>
  <q-page class="q-pa-md">
    <h1>{{ t('customer.title') }}</h1>
    <div class="row q-gutter-md q-mb-md">
      <q-input outlined debounce="300" v-model="keyword" :placeholder="t('button.search')">
        <template v-slot:append>
          <q-icon name="search" />
        </template>
      </q-input>
      <q-space></q-space>
      <q-btn color="accent" icon="add" to="./customers/create" :label="t('customer.create')" />
    </div>
    <q-table :rows="customers" :columns="columns" :loading="loading" :filter="keyword" :filter-method="search"
      row-key="id">
      <template v-slot:body-cell-actions="props">
        <q-td :props="props" auto-width style="min-width: 120px;">
          <q-btn icon="edit" :to="'./customers/' + props.row.id + '/edit'" round text-color="grey-7" />
          <q-btn icon="delete" @click="deleteCustomer(props.row)" round class="q-ml-sm" text-color="grey-7" />
        </q-td>
      </template>
    </q-table>
  </q-page>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import api, { Customer } from '../../../services/api';
import { useCurrentuser } from '../../../share/currentuser';
import * as ui from '../../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const loading = ref(false)
const customers = ref<Customer[]>([])
const keyword = ref('')
const columns = [
  { name: 'name', label: t('customer.name'), align: 'left' as const, field: 'name', sortable: true },
  { name: 'phone', label: t('customer.phone'), align: 'left' as const, field: 'phone', sortable: true },
  { name: 'dob', label: t('customer.dob'), align: 'left' as const, field: 'dob', sortable: true },
  { name: 'address', label: t('customer.address'), align: 'left' as const, field: 'address', sortable: true },
  { name: 'customerType', label: t('customer.type'), align: 'left' as const, field: 'customerType', sortable: true },
  { name: 'mst', label: t('customer.tax_code'), align: 'left' as const, field: 'mst', sortable: true },
  { name: 'cmnd', label: t('customer.personal_code'), align: 'left' as const, field: 'cmnd', sortable: true },
  { name: 'email', label: t('customer.email'), align: 'left' as const, field: 'email', sortable: true },
  { name: 'note', label: t('customer.note'), align: 'left' as const, field: 'note', sortable: true },
  { name: 'companyName', label: t('customer.company_name'), align: 'left' as const, field: 'companyName', sortable: true },
  { name: 'actions', label: t('customer.action'), align: 'right' as const, field: '', sortable: false }
];

function search(rows, terms) {
  const lowerTerms = terms ? terms.toLowerCase() : ""
  return lowerTerms != "" ? rows.filter(row => row.phone.includes(lowerTerms)) : customers
}
async function fetchCustomers() {
  try {
    loading.value = true;
    const res = await api.api.customer.getCustomers(userInfo.value.businessId);
    customers.value = res;
    loading.value = false;

  } catch {
    ui.error(t('error.unknown'))
  }
}

async function deleteCustomer(customer) {
  try {
    loading.value = true;
    await api.api.customer.deleteCustomer(customer.id);
    await fetchCustomers();
    loading.value = false;
    ui.success("delete sucessfull")
  } catch {
    ui.error(t('error.unknown'))
  }
}
onMounted(async () => {
  await fetchCustomers()
})
</script>