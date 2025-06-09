<template>
    <q-toolbar>
        <q-btn v-if="$q.screen.lt.md" flat round icon="menu" @click="$emit('toggleDrawer')" />

        <q-toolbar-title :shrink="true" class="row no-wrap items-center">
            <a href="/home" style="display: flex; align-items: center;" v-if="currentUser.hasRole('admin')">
                <img alt="Logo" src="../../assets/image.png" style="height: 60px; width: auto; margin-right: 12px" />
            </a>

            <a href="/owner/dashboard" style="display: flex; align-items: center;"
                v-else-if="currentUser.hasRole('owner') || currentUser.hasRole('staff')">
                <img alt="Logo" src="../../assets/image.png" style="height: 60px; width: auto; margin-right: 12px" />
            </a>
            <a href="/" style="display: flex; align-items: center;" v-else>
                <img alt="Logo" src="../../assets/image.png" style="height: 60px; width: auto; margin-right: 12px" />
            </a>


        </q-toolbar-title>
        <!-- desktop -->
        <template v-if="$q.screen.gt.sm">

            <!-- <q-btn :label="t('menu.home')" to="/home" class="q-ml-md" stretch flat></q-btn> -->
            <!-- <template v-if="currentUser.hasRole('admin') || currentUser.hasRole('Staff')">
                <q-btn-dropdown :label="t('menu.products')" stretch flat no-caps>
                    <q-list style="min-width: 180px">
                        <q-item v-if="currentUser.hasPermission('products:manage')" to="/admin/products" clickable>
                            <q-item-section>{{ t('menu.products') }}</q-item-section>
                        </q-item>
                        <q-item v-if="currentUser.hasPermission('product_data:manage')" to="/admin/products_data"
                            clickable>
                            <q-item-section>{{ t('menu.products_data') }}</q-item-section>
                        </q-item>
                        <q-item v-if="currentUser.hasPermission('requests:manage')" to="/admin/requests" clickable>
                            <q-item-section>{{ t('menu.requests') }}</q-item-section>
                        </q-item>
                    </q-list>
                </q-btn-dropdown>
                <q-btn-dropdown :label="t('menu.agreements')" stretch flat no-caps>
                    <q-list style="min-width: 180px">
                        <q-item v-if="currentUser.hasPermission('agreements:manage')" to="/admin/agreements" clickable>
                            <q-item-section>{{ t('menu.agreements') }}</q-item-section>
                        </q-item>
                        <q-item to="/admin/user_agreements" clickable>
                            <q-item-section>{{ t('menu.user_agreements') }}</q-item-section>
                        </q-item>
                    </q-list>
                </q-btn-dropdown>
                <q-btn-dropdown
                    v-if="currentUser.hasPermission('users:manage') || currentUser.hasPermission('users:manage') || currentUser.hasPermission('user_activities:manage')"
                    :label="t('menu.users')" stretch flat no-caps>
                    <q-list style="min-width: 180px">
                        <q-item v-if="currentUser.hasPermission('users:manage')" to="/admin/users" clickable>
                            <q-item-section>{{ t('menu.users') }}</q-item-section>
                        </q-item>
                        <q-item v-if="currentUser.hasPermission('roles:manage')" to="/admin/roles" clickable>
                            <q-item-section>{{ t('menu.roles') }}</q-item-section>
                        </q-item>
                        <q-item v-if="currentUser.hasPermission('user_activities:manage')" to="/admin/user_activities"
                            clickable>
                            <q-item-section>{{ t('menu.user_activities') }}</q-item-section>
                        </q-item>
                    </q-list>
                </q-btn-dropdown>
                <q-btn stretch flat :label="t('menu.system_settings')" to="/admin/system_settings" v-if="currentUser.hasRole('Admin')" />

            </template> -->
            <!-- <template v-else>
                <q-btn stretch flat :label="t('menu.products')" to="/products/product_intro" />
                <q-btn stretch flat :label="t('menu.contact_us')" to="/contact" />
            </template> -->
            <template v-if="currentUser.hasRole('admin')">
                <q-btn-dropdown label="User" stretch flat no-caps>
                    <q-list style="min-width: 180px">
                        <q-item to="/admin/users" clickable>
                            <q-item-section>Users</q-item-section>
                        </q-item>
                        <q-item to="/admin/roles" clickable>
                            <q-item-section>Roles</q-item-section>
                        </q-item>

                    </q-list>
                </q-btn-dropdown>
                <q-space />
            </template>
            <template v-if="currentUser.hasRole('owner') || currentUser.hasRole('staff')">
                <q-btn-dropdown :label="t('product.title')" stretch flat no-caps>
                    <q-list style="min-width: 180px">
                        <q-item to="/owner/products" clickable>
                            <q-item-section>{{ t('product.title') }}</q-item-section>
                        </q-item>
                        <q-item to="/owner/branchProduct" clickable>
                            <q-item-section>{{ t('branch_product.title') }}</q-item-section>
                        </q-item>
                        <q-item to="/owner/categories" clickable>
                            <q-item-section>{{ t('category.title') }}</q-item-section>
                        </q-item>
                    </q-list>
                </q-btn-dropdown>
                <q-btn-dropdown :label="t('partner.title')" stretch flat no-caps>
                    <q-list style="min-width: 180px">
                        <q-item to="/owner/customers" clickable>
                            <q-item-section>{{ t('customer.title') }}</q-item-section>
                        </q-item>
                        <q-item to="/owner/suppliers" clickable>
                            <q-item-section>{{ t('supplier.title') }}</q-item-section>
                        </q-item>

                    </q-list>
                </q-btn-dropdown>
                <q-btn-dropdown :label="t('transaction.title')" stretch flat no-caps>
                    <q-list style="min-width: 180px">
                        <q-item to="/owner/purchaseOrders" clickable>
                            <q-item-section>{{ t('purchase_order.title') }}</q-item-section>
                        </q-item>
                        <q-item to="/owner/saleOrders" clickable>
                            <q-item-section>{{ t('sale_order.title') }}</q-item-section>
                        </q-item>


                    </q-list>
                </q-btn-dropdown>
                <q-btn-dropdown :label="t('staff.title')" stretch flat no-caps>
                    <q-list style="min-width: 180px">
                        <q-item to="/owner/staffs" clickable>
                            <q-item-section>{{ t('staff.title') }}</q-item-section>
                        </q-item>


                    </q-list>
                </q-btn-dropdown>

            </template>
            <template v-else>
                <q-btn stretch flat :label="t('home')" to="/" />

            </template>
            <q-space />
            <!-- right side -->


            <template v-if="currentUser.hasloggedIn()">
                <!-- <q-btn stretch flat :label="t('menu.my_instances')" to="/my_data"
                    v-if="currentUser.hasRole('Customer')" /> -->
                <q-btn round flat>
                    <avatar :user="{ username: userInfo.username, full_name: '', profile_img: '' }" size="28px">
                    </avatar>
                    <q-menu>
                        <q-list style="min-width: 180px">
                            <q-item to="/account" clickable v-close-popup>
                                <q-item-section avatar>
                                    <q-icon color="grey" name="person_outline" />
                                </q-item-section>
                                <q-item-section>{{ t('current_user.account') }}</q-item-section>
                            </q-item>
                            <q-item clickable v-close-popup @click="logout">
                                <q-item-section avatar>
                                    <q-icon color="grey" name="logout" />
                                </q-item-section>
                                <q-item-section>{{ t('current_user.logout') }}</q-item-section>
                            </q-item>
                        </q-list>
                    </q-menu>
                </q-btn>
            </template>
            <template v-else>
                <q-btn rounded outline to="/register">{{ t('register.title') }} </q-btn>
                <q-btn rounded outline to="/login">{{ t('login.title') }}</q-btn>
            </template>
        </template>
        <!-- mobile -->
        <template v-else>
            <q-space />
        </template>
        <q-btn flat round>
            <q-icon name="language" />
            <q-menu>
                <q-list>
                    <q-item v-for="item in localeOptions" :key="item.value" clickable @click="locale = item.value">
                        <q-item-section>
                            {{ item.label }}
                        </q-item-section>
                    </q-item>
                </q-list>
            </q-menu>
        </q-btn>


    </q-toolbar>
</template>

<script setup lang="ts">

import { useI18n } from 'vue-i18n'

const { locale } = useI18n({ useScope: 'global' })

const { t } = useI18n();
const localeOptions = [
    { value: 'en-US', label: 'English' },
    { value: 'vi', label: 'Vietnam' }
]

import { useCurrentuser } from '../../share/currentuser';
import Avatar from '../../components/UserAvatar.vue';
// import Notifications from './notifications.vue';
// import { CONFIG } from '@/config'
// import * as ui from '@/utils/ui';
// import  api  from '@/services/api';
// const { t } = useI18n();
const currentUser = useCurrentuser()
const userInfo = currentUser.info

function logout() {
    // ui.confirm('Are you sure', 'Logout', () => currentUser.logout(), null)
    currentUser.logout()
}


</script>