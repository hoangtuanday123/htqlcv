<template>
    <q-toolbar>
        <q-btn v-if="$q.screen.lt.md" flat round icon="menu" @click="$emit('toggleDrawer')" />

        <q-toolbar-title :shrink="true" class="row no-wrap items-center">
            <!-- <img style="height: 16px;" src="../../assets/connexus_white.png"> -->
            <span style="margin-left: 10px; font-family: 'Sui Generis Rg'; font-size: 22px;">APP</span>
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
                <q-btn-dropdown label="Products" stretch flat no-caps>
                    <q-list style="min-width: 180px">
                        <q-item to="/admin/products" clickable>
                            <q-item-section>Products</q-item-section>
                        </q-item>
                        <q-item to="/admin/branchProduct" clickable>
                            <q-item-section>Branch Product</q-item-section>
                        </q-item>
                        <q-item to="/admin/categories" clickable>
                            <q-item-section>Categories</q-item-section>
                        </q-item>
                    </q-list>
                </q-btn-dropdown>
                <q-btn-dropdown label="Partner" stretch flat no-caps>
                    <q-list style="min-width: 180px">
                        <q-item to="/admin/customers" clickable>
                            <q-item-section>Customer</q-item-section>
                        </q-item>
                        <q-item to="/admin/suppliers" clickable>
                            <q-item-section>Supplier</q-item-section>
                        </q-item>

                    </q-list>
                </q-btn-dropdown>
                <q-btn-dropdown label="Transactions" stretch flat no-caps>
                    <q-list style="min-width: 180px">
                        <q-item to="/admin/purchaseOrders" clickable>
                            <q-item-section>Purchase Orders</q-item-section>
                        </q-item>
                        <q-item to="/admin/saleOrders" clickable>
                            <q-item-section>Sale Orders</q-item-section>
                        </q-item>


                    </q-list>
                </q-btn-dropdown>
            </template>
            <q-space />
            <!-- right side -->

            <template v-if="currentUser.hasloggedIn()" class="">
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
                                <q-item-section>Account</q-item-section>
                            </q-item>
                            <q-item clickable v-close-popup @click="logout">
                                <q-item-section avatar>
                                    <q-icon color="grey" name="logout" />
                                </q-item-section>
                                <q-item-section>Logout</q-item-section>
                            </q-item>
                        </q-list>
                    </q-menu>
                </q-btn>
            </template>
            <template v-else class="items-center no-wrap">
                <q-btn rounded outline to="/register">Resgister </q-btn>
                <q-btn rounded outline to="/login">Login</q-btn>
            </template>
        </template>
        <!-- mobile -->
        <template v-else>
            <q-space />
        </template>
        <!-- <q-btn flat round>
            <q-icon name="language" />
            <q-menu>
                <q-list>
                    <q-item v-for="locale in supportLocales" :key="locale.value" clickable
                        @click="selectLanguage(locale.value)">
                        <q-item-section>
                            {{ locale.label }}
                        </q-item-section>
                    </q-item>
                </q-list>
            </q-menu>
        </q-btn> -->

    </q-toolbar>
</template>

<script setup lang="ts">
import { ref } from 'vue';
// import router from '@/router';
// import { setI18nLanguage } from '@/i18n';
import { useQuasar } from 'quasar';
import languages from 'quasar/lang/index.json'
// import { useI18n } from 'vue-i18n';

import { useCurrentuser } from '../../share/currentuser';
import Avatar from '../..//components/avatar.vue';
// import Notifications from './notifications.vue';
// import { CONFIG } from '@/config'
// import * as ui from '@/utils/ui';
// import  api  from '@/services/api';
// const { t } = useI18n();
const currentUser = useCurrentuser()
const userInfo = currentUser.info
const $q = useQuasar();

// const appLanguages = languages.filter(lang =>
//     ['en-US', 'vi'].includes(lang.isoName)
// )
// const supportLocales = appLanguages.map(lang => ({
//     label: lang.nativeName, value: lang.isoName
// }))

// const selectedLocale = ref($q.lang.isoName);

// const selectLanguage = (locale) => {
//     selectedLocale.value = locale;
//     changeLanguage();
//     router.go(0);
// };
// const loading = ref(false)
// const changeLanguage = () => {
//     setI18nLanguage(selectedLocale.value);
// };

function logout() {
    // ui.confirm('Are you sure', 'Logout', () => currentUser.logout(), null)
    currentUser.logout()
}


</script>