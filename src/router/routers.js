import Main from '@/view/main/Main';
export default [
  {
    path: '/',
    name: '_home',
    redirect: '/home',
    component: Main,
    children: [
      {
        path: '/home',
        name: 'home',
        component: () => import('@/view/home/Home')
      }
    ]
  },
  {
    path: '/photo',
    name: 'photo',
    component: Main,
    children: [
      {
        path: 'wall',
        name: 'photo-wall',
        meta: {
          title: '照片墙',
          icon: 'mdi-wall',
          showInMenu: true
        },
        component: () => import('@/view/photo/PhotoWall')
      },
      {
        path: 'album',
        name: 'photo-album',
        meta: {
          title: '相簿',
          icon: 'mdi-google-photos',
          showInMenu: true
        },
        component: () => import('@/view/photo/PhotoWall')
      },
      {
        path: 'time',
        name: 'photo-time',
        meta: {
          title: '时间',
          icon: 'mdi-camera-timer',
          showInMenu: true
        },
        component: () => import('@/view/photo/PhotoWall')
      },
      {
        path: 'people',
        name: 'photo-people',
        meta: {
          title: '人物',
          icon: 'mdi-tooltip-account',
          showInMenu: true
        },
        component: () => import('@/view/photo/PhotoWall')
      },
      {
        path: 'location',
        name: 'photo-location',
        meta: {
          title: '地点',
          icon: 'mdi-crosshairs-gps',
          showInMenu: true
        },
        component: () => import('@/view/photo/PhotoWall')
      }
    ]
  }
]
