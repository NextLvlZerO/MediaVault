import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import Components from 'unplugin-vue-components/vite'
import MotionResolver from 'motion-v/resolver'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    Components({
      dts: true,
      resolvers: [
        MotionResolver()
      ],
    }),
    vueDevTools(),
  ],

  optimizeDeps: {
    include: ['@stomp/stompjs', 'sockjs-client']
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      events: "rollup-plugin-node-polyfills/polyfills/events",
      util: "rollup-plugin-node-polyfills/polyfills/util",
      buffer: "rollup-plugin-node-polyfills/polyfills/buffer-es6",
    },
  },
})
