<template>
  <q-page class='q-pa-md bg-grey-1'>
    <div class='row q-col-gutter-xl items-center'>
      <!-- Left: Text content -->
      <div class='col-12 col-md-6'>
        <div class='text-h4 text-bold q-mb-md'>
          Phần mềm quản lý chuỗi cửa hàng<br />
          <span class='text-primary'>Bán lẻ - Retail tốt nhất</span>
        </div>
        <ul class='text-subtitle1 q-pl-md'>
          <li>✓ quản lý chuỗi chuyên nghiệp</li>
          <li>✓ Quản lý hàng hóa chặt chẽ theo mã vạch</li>
          <li>✓ Quản lý kho hàng</li>
        </ul>
        <q-btn label='DÙNG THỬ MIỄN PHÍ!' color='orange' text-color='white' class='q-mt-md' size='lg' unelevated rounded
          to='/register' />
      </div>

      <!-- Right: Image + icons -->
      <div class='col-12 col-md-6 relative-position'>
        <q-img src='/index/quan-ly-cua-hang.png' ratio='16/9' style='height: 300px;' class='rounded-borders shadow-4' />

      </div>
    </div>
    <q-separator class='q-my-md' />
    <div class='row items-center q-gutter-md' style='max-width: 1000px; width: 100%; display: flex;'>
      <!-- Ảnh bên trái -->
      <div class='col-12 col-md-5'>
        <div class='image-wrapper' style='width: 100%;'>
          <q-img src='index/hinh-anh-ve-suy-nghi.png' alt='Thinking woman' ratio='1' class='rounded-borders'
            style='width: 100%; height: auto;' />
        </div>
      </div>

      <!-- Nội dung bên phải -->
      <div class='col-12 col-md-6'>
        <h6 class='text-center text-md-left text-primary q-mb-md' style='font-weight: 600;'>
          Những khó khăn thường gặp phải khi kinh doanh chuỗi bán lẻ
        </h6>
        <q-list dense class='text-primary' style='font-style: italic; font-weight: 500;'>
          <q-item v-for='(item, index) in difficulties' :key='index' class='q-py-xs'>
            <q-item-section side>
              <q-icon name='close' color='red' />
            </q-item-section>
            <q-item-section>
              {{ item }}
            </q-item-section>
          </q-item>
        </q-list>
      </div>
    </div>
    <q-separator class='q-my-md' />
    <div class='column q-gutter-xl' style='max-width: 1000px; margin: auto;'>
      <div v-for='(section, index) in sections' :key='index' class='row q-gutter-md items-start'>
        <!-- Icon -->
        <div class='col-auto'>
          <q-avatar size='48px' color='orange' text-color='white' class='flex flex-center'>
            <q-icon :name='section.icon' size='28px' />
          </q-avatar>
        </div>
        <!-- Phần nội dung bên trái -->
        <!-- Nội dung -->
        <div class='col'>
          <div class='text-h6 text-weight-bold q-mb-sm'>{{ section.title }}</div>
          <ul class='q-pl-md' style='list-style: disc;'>
            <li v-for='(line, i) in section.content' :key='i'>{{ line }}</li>
          </ul>
        </div>


      </div>
      <q-separator class='q-my-md' />
      <div class='text-center'>
        <h4 class='text-bold'>BÁO GIÁ PHẦN MỀM QUẢN LÝ BÁN LẺ, QUÁN CAFE, NHÀ HÀNG, TRÀ SỮA</h4>
        <p class='q-mt-md text-subtitle1'>
          Chỉ <b>5,000 đồng/ngày</b> giúp giảm đến <b>70%</b> thời gian quản lý để bạn thành thơi cùng gia đình bạn bè
          đi khắp nơi!
        </p>
        <p class='text-subtitle2 q-mt-sm'>
          Trải nghiệm ngay <span class='text-pink text-bold'>MIỄN PHÍ</span> cho tất cả gói dịch vụ
        </p>
      </div>

      <q-btn label='Đăng ký' color='deep-orange' class='col-2' unelevated type='button' to='/register' />

      <!-- Phần hình ảnh bên phải -->
      <div class='col-12 col-md-6'>
        <q-img src='/index/hinh-anh-ve-suy-nghi.png' alt='Customer scanning' ratio='16/9' class='rounded-borders'
          style='max-width: 100%;' />
      </div>
    </div>


    <!-- Hotline -->
    <div class='fixed-bottom-left q-mb-md q-ml-md'>
      <q-banner class='bg-orange text-white rounded-borders q-pa-sm shadow-4'>
        📞 <span class='text-bold'>0843055059</span>
      </q-banner>
    </div>
  </q-page>
</template>

<script setup lang='ts'>
import { ref } from 'vue';
// import { Todo, Meta } from 'components/models';
// import ExampleComponent from 'components/ExampleComponent.vue';
const difficulties = ref([
  'Không thể kiểm soát được số lượng hàng bán ra hay tồn kho của từng cửa hàng, đại lý',
  'Quản lý bán hàng trên các kênh, theo dõi đối tác vận chuyển, tiền COD gặp nhiều khó khăn',
  'Việc kiểm tra doanh thu, lãi lỗ của từng cửa hàng tốn nhiều thời gian',
  'Không kiểm soát được hoạt động bán hàng của từng nhân viên khi không có mặt tại cửa hàng',
  'Các cửa hàng trong chuỗi gặp sự cố trong khâu thanh toán, gây ảnh hưởng đến trải nghiệm của khách hàng',
  'Thời gian bán hàng, thanh toán chậm làm mất lòng khách hàng'
])

const sections = ref([
  {
    icon: 'percent',
    title: 'Quản lý các chương trình khuyến mãi',
    content: [
      'Giúp chủ cửa hàng xây dựng các chương trình khuyến mãi theo từng thời điểm, theo hình thức khuyến mãi giảm giá hay quà tặng',
      'Lập kế hoạch thời gian, chi nhánh, nguồn đơn hay đối tượng khách hàng được áp dụng khuyến mãi',
      'Hỗ trợ tạo mã giảm giá thông qua mã phát hành Coupon với từng tệp khách hàng tiềm năng'
    ]
  },
  {
    icon: 'list',
    title: 'Quản lý khách hàng',
    content: [
      'Lưu trữ thông tin khách hàng như tên, SĐT, địa chỉ và lịch sử mua hàng để xây dựng các chương trình khuyến mãi phù hợp',
      'Tích điểm nhanh chóng khi mua hàng tại các cửa hàng trên chuỗi, tạo chính sách ưu đãi hoặc đổi quà phù hợp cho khách hàng khi đã tích đủ điểm',
      'Theo dõi công nợ khách hàng một cách chính xác, hạn chế tối đa các sai sót trong quá trình bán hàng'
    ]
  },
  {
    icon: 'store',
    title: 'Quản lý kho hàng chặt chẽ',
    content: [
      'Nắm bắt số lượng của từng sản phẩm chi tiết theo từng mẫu mã, màu sắc, kích thước, chất liệu',
      'Phần mềm quản lý kho cập nhật liên tục các thông tin tồn kho, xuất kho,... của hàng hóa',
      'Theo dõi và quản lý lịch sử nhập hàng, công nợ nhà cung cấp của từng chi nhánh, đánh giá khả năng tiêu thụ của từng chi nhánh',
      'Kiểm kho bằng mã vạch, đồng bộ kho và quản lý xuất nhập ngay tại phần mềm giúp giảm 50% nhân lực',
      'Biết được các mặt hàng bán chạy hay tiêu thụ chậm để có chính sách nhập hàng hay xả kho phù hợp'
    ]
  },
  {
    icon: 'landscape',
    title: 'Xem báo cáo hoạt động kinh doanh từ xa',
    content: [
      'Quản lý chi tiết các hoạt động kinh doanh, nhập – xuất kho của toàn hệ thống theo thời gian thực chỉ với chiếc điện thoại di động'
    ]
  },
  {
    icon: 'landscape',
    title: 'Quản lý kinh doanh toàn hệ thống',
    content: [
      'Hỗ trợ lưu sổ quỹ, công nợ của nhà cung cấp, đối tác vận chuyển, khách hàng chặt chẽ cho từng chi nhánh',
      'Lưu vết từng thao tác bán hàng, xuất nhập kho của từng cửa hàng để đối soát khi cần'
    ]
  },
  {
    icon: 'attach_money',
    title: 'Quản lý dòng tiền, công nợ',
    content: [
      'Hỗ trợ lưu sổ quỹ, công nợ của nhà cung cấp, đối tác vận chuyển, khách hàng chặt chẽ',
      'Lưu vết từng thao tác bán hàng, xuất nhập kho để đối soát khi cần'
    ]
  }
])
defineOptions({
  name: 'IndexPage'
});


</script>
