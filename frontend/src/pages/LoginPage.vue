<template>
    <q-page class="row items-center justify-evenly">

        <q-card>
            <q-card-section>
                <div class="text-h6">{{ t('login.title') }}</div>

            </q-card-section>
            <q-card-section>
                <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-md">
                    <q-input filled v-model="username" :label="t('login.username')" :hint="t('login.username')"
                        lazy-rules :rules="[val => val && val.length > 0 || t('login.invalid')]" />

                    <q-input filled v-model="password" :label="t('login.password')" :hint="t('login.password')"
                        lazy-rules :rules="[val => val && val.length > 0 || t('login.invalid')]" />

                    <!-- <q-toggle v-model="accept" label="I accept the license and terms" /> -->
                    <div class="row justify-end">
                        <a class="text-h7" href="forgot_password">{{ t('login.forgotPassword') }}
                        </a>
                    </div>
                    <div>
                        <q-btn :label="t('login.title')" type="submit" :loading="loading" color="primary" />
                        <q-btn :label="t('login.reset')" type="reset" color="primary" flat class="q-ml-sm" />
                    </div>
                </q-form>
            </q-card-section>

        </q-card>
    </q-page>
</template>
<script setup lang="ts">
import api from '../services/api';
import { ref } from 'vue'

import { userStore } from '../stores/user'
import pinia from '../stores'
import router from '../router/index'
import * as ui from '../utils/ui'
import { useI18n } from 'vue-i18n';
const { t } = useI18n();
const username = ref('')
const password = ref('')
// const accept = ref(false)

const loading = ref(false)

const _userStore = userStore(pinia())


async function onSubmit() {
    loading.value = true
    try {
        const loginreq = {
            username: username.value,
            password: password.value

        }
        const token = await api.api.auth.login(loginreq);

        if (token) {
            _userStore.saveToken(token)
            const user = await api.api.user.getCurrentUser()

            _userStore.saveUserInfo({ id: user['id'], username: user['username'], email: user['email'], phoneNumber: user['phoneNumber'], roles: user['roles'], businessId: user['businessId'] })
            if (user['roles'][0] == 'owner' || user['roles'][0] == 'staff') {
                router.push({ path: '/owner/dashboard' })
            }
            else {
                router.push({ path: '/home' })
            }

        }
    }
    catch {
        ui.error(t('error.unknown'))
    }
    finally {

        loading.value = false
    }

}

async function onReset() {
    username.value = ''
    password.value = ''
    // accept.value = false
}

</script>