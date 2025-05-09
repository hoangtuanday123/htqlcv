<!-- <template>
  <div class="q-pa-md">
    <q-layout view="hHh Lpr lff">
      <q-header elevated :class="$q.dark.isActive ? 'bg-secondary' : 'bg-black'">
        <q-toolbar>
          <q-btn v-if="currentuser.hasloggedIn()" flat @click="drawer = !drawer" round dense icon="menu" />
          <q-toolbar-title>Header</q-toolbar-title>
          <q-btn v-if="!currentuser.hasloggedIn()" flat to="/login">Login</q-btn>
          <q-btn v-else>
            <q-chip>
              <q-avatar>
                <img src="https://cdn.quasar.dev/img/boy-avatar.png">
              </q-avatar>
              {{ info.fullname }}
            </q-chip>
            <q-menu v-model="showing">
              <q-list style="min-width: 100px">
                <q-item clickable v-close-popup>
                  <q-item-section>Profile</q-item-section>
                </q-item>
                
                <q-separator />
                <q-item clickable v-close-popup @click="logout">
                  <q-item-section>Logout</q-item-section>
                </q-item>
                
              </q-list>
            </q-menu>
          </q-btn>
        </q-toolbar>
      </q-header>

      <q-drawer v-if="currentuser.hasloggedIn()" v-model="drawer" show-if-above :width="200" :breakpoint="500" bordered
        :class="$q.dark.isActive ? 'bg-grey-9' : 'bg-grey-3'">
        <q-scroll-area class="fit">
          <q-list>

            <template v-for="(menuItem, index) in menuList" :key="index">
              <q-item clickable :active="menuItem.label === 'Outbox'" :to=menuItem.link v-ripple>
                <q-item-section avatar >
                  <q-icon :name="menuItem.icon" />
                </q-item-section>
                <q-item-section>
                  {{ menuItem.label }}
                </q-item-section>
              </q-item>
              <q-separator :key="'sep' + index" v-if="menuItem.separator" />
            </template>

          </q-list>
        </q-scroll-area>
      </q-drawer>

      <q-page-container>
        <router-view />
      </q-page-container>
    </q-layout>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useCurrentuser } from '../share/currentuser'
const currentuser = useCurrentuser()
const info = currentuser.info
const showing= ref(false)
const menuList = [
  {
    icon: 'meeting_room',
    label: 'Department',
    link:'/admin/department',
    separator: false
  },
  {
    icon: 'table_view',
    label: 'Major',
    link:'/admin/major',
    separator: false
  },
  {
    icon: 'school',
    label: 'Class',
    link:'/admin/classroom',
    separator: false
  },
  {
    icon: 'book',
    label: 'Course',
    link:'/admin/course',
    separator: false
  },
  {
    icon: 'import_contacts',
    label: 'Open Courses',
    link:'/admin/opencourse',
    separator: false
  },
  {
    icon: 'person',
    label: 'Users',
    link:'/admin/user',
    separator: false
  },{
    icon: 'app_registration',
    label: 'Enrollment',
    link:'/admin/enrollment',
    separator: false
  },
  {
    icon: 'error',
    label: 'Spam',
    separator: true
  }
  ,
  
  {
    icon: 'settings',
    label: 'Settings',
    separator: false
  },
  {
    icon: 'feedback',
    label: 'Send Feedback',
    separator: false
  },
  {
    icon: 'help',
    iconColor: 'primary',
    label: 'Help',
    separator: false
  }
]
const drawer = ref(false)
function logout(){
  currentuser.logout()
}
// export default {
//   setup () {
//     return {
//       drawer: ref(false),
//       menuList
//     }
//   }
// }
</script> -->
