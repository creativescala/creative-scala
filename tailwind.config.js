/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/templates/*.html", "./target/pages/**/*.html"],
  theme: {
    extend: {
      width: {
        '128': '32rem',
      }
    },
    fontFamily: {
      sans: ['Source Sans Pro', 'sans-serif'],
      serif: ['Crimson Pro', 'serif']
    }
  },
  plugins: [],
}
