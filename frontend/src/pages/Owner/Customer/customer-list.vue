<template>
  <q-page class="q-pa-md">
    <h1>Customers</h1>
    <div class="row q-gutter-md q-mb-md">
      <q-input outlined debounce="300" v-model="keyword" placeholder="Search">
        <template v-slot:append>
          <q-icon name="search" />
        </template>
      </q-input>
      <q-space></q-space>
      <q-btn color="accent" icon="add" to="./customers/create" label="Create Customer" />
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
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const loading = ref(false)
const customers = ref<Customer[]>([])
const keyword = ref('')
const columns = [
  { name: 'name', label: 'Name', align: 'left' as const, field: 'name', sortable: true },
  { name: 'phone', label: 'Phone', align: 'left' as const, field: 'phone', sortable: true },
  { name: 'dob', label: 'Date Of Birth', align: 'left' as const, field: 'dob', sortable: true },
  { name: 'address', label: 'Address', align: 'left' as const, field: 'address', sortable: true },
  { name: 'customerType', label: 'Customer Type', align: 'left' as const, field: 'customerType', sortable: true },
  { name: 'mst', label: 'Tax Code', align: 'left' as const, field: 'mst', sortable: true },
  { name: 'cmnd', label: 'Personal ID', align: 'left' as const, field: 'cmnd', sortable: true },
  { name: 'email', label: 'Email', align: 'left' as const, field: 'email', sortable: true },
  { name: 'note', label: 'Note', align: 'left' as const, field: 'note', sortable: true },
  { name: 'companyName', label: 'Company Name', align: 'left' as const, field: 'companyName', sortable: true },
  { name: 'actions', label: 'Actions', align: 'right' as const, field: '', sortable: false }
];

function search(rows, terms) {
  const lowerTerms = terms ? terms.toLowerCase() : ""
  return lowerTerms != "" ? rows.filter(row => row.phone.includes(lowerTerms)) : customers
}
async function fetchCustomers() {
  loading.value = true;
  const res = await api.api.customer.getCustomers(userInfo.value.businessId);
  customers.value = res;
  loading.value = false;
}

async function deleteCustomer(customer) {
  loading.value = true;
  await api.api.customer.deleteCustomer(customer.id);
  fetchCustomers();
  loading.value = false;
}
onMounted(async () => {
  await fetchCustomers()
})
</script>