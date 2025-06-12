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
                <q-btn stretch flat :label="t('AI')" to="/owner/ai-service" />
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

const currentUser = useCurrentuser()
const userInfo = currentUser.info

function logout() {
    // ui.confirm('Are you sure', 'Logout', () => currentUser.logout(), null)
    currentUser.logout()
}


</script>