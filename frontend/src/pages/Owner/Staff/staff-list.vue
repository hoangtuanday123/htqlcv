<template>
  <q-page class="q-pa-md">
    <h1>{{ t('staff.title') }}</h1>
    <div class="row q-gutter-md q-mb-md">
      <q-input outlined debounce="300" v-model="keyword" :placeholder="t('button.search')">
        <template v-slot:append>
          <q-icon name="search" />
        </template>
      </q-input>
      <q-space></q-space>
      <q-btn color="accent" icon="add" to="./staffs/create" :label="t('staff.create')" />
    </div>
    <q-table :rows="users" :columns="columns" :loading="loading" :filter="keyword" :filter-method="search" row-key="id">
      <template v-slot:body-cell-actions="props">
        <q-td :props="props" auto-width style="min-width: 120px;">
          <q-btn icon="edit" :to="'./Staffs/' + props.row.id + '/edit'" round text-color="grey-7" />
          <!-- <q-btn icon="delete" @click="deleteRole(props.row)" round class="q-ml-sm" text-color="grey-7"/> -->
        </q-td>
      </template>
    </q-table>
  </q-page>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import api, { User } from '../../../services/api';
import * as ui from '../../../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
import { useCurrentuser } from '../../../share/currentuser';
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const loading = ref(false)
const users = ref<User[]>([])
const keyword = ref('')
const columns = [
  { name: 'username', label: t('staff.username'), align: 'left' as const, field: 'username', sortable: true },
  { name: 'email', label: t('staff.email'), align: 'left' as const, field: 'email', sortable: true },
  { name: 'phoneNumber', label: t('staff.phone'), align: 'left' as const, field: 'phoneNumber', sortable: true },
  { name: 'actions', label: t('staff.action'), align: 'right' as const, field: '', sortable: false }
];

function search(rows, terms) {
  const lowerTerms = terms ? terms.toLowerCase() : ''
  return lowerTerms != '' ? rows.filter(row => row.name.includes(lowerTerms)) : users
}
async function fetch() {
  try {
    loading.value = true;
    const res = await api.api.user.getStaffs(String(userInfo.value.businessId));
    users.value = res;
    loading.value = false;
  } catch {
    ui.error(t('error.unknown'))
  }
}
onMounted(async () => {
  await fetch()
})
</script>