<template>
  <q-list padding>
    <q-item v-ripple clickable>
      <q-item-section>
        <router-link to="/" exact>{{ t('home') }}</router-link>
      </q-item-section>
      <q-item-section side>
        <q-btn
          dense
          style="height: 24px"
          icon="close"
          @click="$emit('toggleDrawer')"
        ></q-btn>
      </q-item-section>
    </q-item>
    <template v-if="currentUser.hasRole('admin')">
      <q-separator />
      <q-list>
        <q-expansion-item icon="person" label="User" dense expand-separator>
          <q-item to="/admin/users" clickable>
            <q-item-section>{{ t('users') }}</q-item-section>
          </q-item>

          <q-item to="/admin/roles" clickable>
            <q-item-section>{{ t('roles') }}</q-item-section>
          </q-item>
        </q-expansion-item>
      </q-list>
    </template>
    <template
      v-else-if="currentUser.hasRole('owner') || currentUser.hasRole('staff')"
    >
      <q-separator />
      <q-list>
        <q-expansion-item
          icon="inventory_2"
          :label="t('product.title')"
          dense
          expand-separator
        >
          <q-item to="/owner/products" clickable>
            <q-item-section>{{ t('product.title') }}</q-item-section>
          </q-item>
          <q-item to="/owner/branchProduct" clickable>
            <q-item-section>{{ t('branch_product.title') }}</q-item-section>
          </q-item>
          <q-item to="/owner/categories" clickable>
            <q-item-section>{{ t('category.title') }}</q-item-section>
          </q-item>
        </q-expansion-item>
        <q-expansion-item
          icon="person"
          :label="t('partner.title')"
          dense
          expand-separator
        >
          <q-item to="/owner/customers" clickable>
            <q-item-section>{{ t('customer.title') }}</q-item-section>
          </q-item>
          <q-item to="/owner/suppliers" clickable>
            <q-item-section>{{ t('supplier.title') }}</q-item-section>
          </q-item>
        </q-expansion-item>
        <q-expansion-item
          icon="real_estate_agent"
          :label="t('transaction.title')"
          dense
          expand-separator
        >
          <q-item to="/owner/purchaseOrders" clickable>
            <q-item-section>{{ t('purchase_order.title') }}</q-item-section>
          </q-item>
          <q-item to="/owner/saleOrders" clickable>
            <q-item-section>{{ t('sale_order.title') }}</q-item-section>
          </q-item>
        </q-expansion-item>
        <q-expansion-item
          icon="person"
          :label="t('staff.title')"
          dense
          expand-separator
        >
          <q-item to="/owner/staffs" clickable>
            <q-item-section>{{ t('staff.title') }}</q-item-section>
          </q-item>
        </q-expansion-item>
      </q-list>
    </template>

    <template v-if="currentUser.hasloggedIn()">
      <q-separator />
      <q-item to="/owner/ai-service">
        <q-item-section>{{ t('AI') }}</q-item-section>
      </q-item>
      <q-item to="/account">
        <q-item-section>{{ t('current_user.account') }}</q-item-section>
      </q-item>
      <q-item clickable @click="logout">
        <q-item-section>{{ t('current_user.logout') }}</q-item-section>
      </q-item>
    </template>
    <template v-else>
      <q-separator />
      <q-item to="/register">
        <q-item-section>{{ t('register.title') }}</q-item-section>
      </q-item>
      <q-item to="/login">
        <q-item-section>{{ t('login.title') }}</q-item-section>
      </q-item>
    </template>
  </q-list>
</template>
<script setup lang="ts">
import { useI18n } from 'vue-i18n';

const { t } = useI18n();

import { useCurrentuser } from '../../share/currentuser';

const currentUser = useCurrentuser();

function logout() {
  // ui.confirm('Are you sure', 'Logout', () => currentUser.logout(), null)
  currentUser.logout();
}
</script>
