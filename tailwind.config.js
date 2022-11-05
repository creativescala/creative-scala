/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/templates/*.html", "./target/pages/**/*.html"],
  theme: {
    extend: {
      width: {
        '128': '32rem',
        '132': '33rem',
        '144': '36rem'
      }
    },
    fontFamily: {
      sans: ['Source Sans Pro', 'sans-serif'],
      serif: ['Crimson Pro', 'serif']
    }
  },
  plugins: [],
}
