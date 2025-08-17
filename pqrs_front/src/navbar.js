class NavbarManager {
  constructor() {
    this.currentPage = this.getCurrentPage()
    this.isAuthenticated = localStorage.getItem("isAuthenticated") === "true"
    this.userEmail = localStorage.getItem("userEmail") || ""
  }

  getCurrentPage() {
    const path = window.location.pathname
    const page = path.split("/").pop() || "index.html"
    return page.replace(".html", "") || "index"
  }

  initTheme() {
    const savedTheme = localStorage.getItem("theme") || "light"
    document.documentElement.classList.toggle("dark", savedTheme === "dark")
  }

  toggleTheme() {
    const isDark = document.documentElement.classList.contains("dark")
    const newTheme = isDark ? "light" : "dark"

    document.documentElement.classList.toggle("dark", newTheme === "dark")
    localStorage.setItem("theme", newTheme)
  }

  render() {
    const navItems = this.isAuthenticated
      ? [
          { name: "Inicio", href: "index.html", id: "index" },
          { name: "Formulario PQRS", href: "formulario.html", id: "formulario" },
        ]
      : [
          { name: "Inicio", href: "index.html", id: "index" },
          { name: "Formulario PQRS", href: "formulario.html", id: "formulario" },
          { name: "Iniciar Sesión", href: "login.html", id: "login" },
          { name: "Registrarse", href: "registro.html", id: "registro" },
        ]

    return `
      <header class="bg-white dark:bg-gray-800 shadow-sm transition-colors duration-300">
        <nav class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
          <div class="flex h-16 items-center justify-between">
            <div class="flex items-center">
              <a href="index.html" class="text-xl font-bold text-blue-600 dark:text-blue-400 hover:text-blue-700 dark:hover:text-blue-300 transition-colors duration-200">
                Sistema PQRS
              </a>
            </div>

            <div class="hidden md:flex md:items-center md:space-x-8">
              ${navItems
                .map(
                  (item) => `
                <a href="${item.href}" class="font-medium transition-colors duration-200 ${
                  this.currentPage === item.id
                    ? "text-blue-600 dark:text-blue-400 border-b-2 border-blue-600 dark:border-blue-400"
                    : "text-gray-600 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400"
                }">
                  ${item.name}
                </a>
              `,
                )
                .join("")}
            </div>

            <div class="hidden md:flex md:items-center md:space-x-4">
              ${
                this.isAuthenticated
                  ? `
                <span class="text-sm text-gray-600 dark:text-gray-300">${this.userEmail}</span>
                <button onclick="navbarManager.logout()" class="px-4 py-2 text-sm font-medium text-red-600 hover:text-red-700 dark:text-red-400 dark:hover:text-red-300 transition-colors duration-200">
                  Cerrar Sesión
                </button>
              `
                  : ""
              }
              <button onclick="navbarManager.toggleTheme()" class="p-2 rounded-md text-gray-600 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors duration-200">
                <svg class="h-5 w-5 dark:hidden" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
                </svg>
                <svg class="h-5 w-5 hidden dark:block" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
                </svg>
              </button>
            </div>

            <div class="flex items-center space-x-2 md:hidden">
              <button onclick="navbarManager.toggleTheme()" class="p-2 rounded-md text-gray-600 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors duration-200">
                <svg class="h-5 w-5 dark:hidden" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
                </svg>
                <svg class="h-5 w-5 hidden dark:block" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
                </svg>
              </button>
              <button onclick="navbarManager.toggleMobileMenu()" class="p-2 rounded-md text-gray-600 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors duration-200">
                <svg id="menu-icon" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
                </svg>
                <svg id="close-icon" class="h-6 w-6 hidden" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
            </div>
          </div>

          <div id="mobile-menu" class="hidden md:hidden">
            <div class="space-y-1 pb-3 pt-2">
              ${navItems
                .map(
                  (item) => `
                <a href="${item.href}" class="block px-3 py-2 text-base font-medium transition-colors duration-200 ${
                  this.currentPage === item.id
                    ? "text-blue-600 dark:text-blue-400 bg-blue-50 dark:bg-blue-900/20"
                    : "text-gray-600 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-700 hover:text-blue-600 dark:hover:text-blue-400"
                }">
                  ${item.name}
                </a>
              `,
                )
                .join("")}
              ${
                this.isAuthenticated
                  ? `
                <div class="px-3 py-2">
                  <span class="text-sm text-gray-600 dark:text-gray-300">${this.userEmail}</span>
                  <button onclick="navbarManager.logout()" class="block w-full mt-2 px-4 py-2 text-sm font-medium text-red-600 hover:text-red-700 dark:text-red-400 dark:hover:text-red-300 transition-colors duration-200">
                    Cerrar Sesión
                  </button>
                </div>
              `
                  : ""
              }
            </div>
          </div>
        </nav>
      </header>
    `
  }

  toggleMobileMenu() {
    const mobileMenu = document.getElementById("mobile-menu")
    const menuIcon = document.getElementById("menu-icon")
    const closeIcon = document.getElementById("close-icon")

    mobileMenu.classList.toggle("hidden")
    menuIcon.classList.toggle("hidden")
    closeIcon.classList.toggle("hidden")
  }

  logout() {
    localStorage.removeItem("isAuthenticated")
    localStorage.removeItem("userEmail")
    window.location.href = "index.html"
  }

  init() {
    this.initTheme()
    document.getElementById("navbar-container").innerHTML = this.render()
  }
}

const navbarManager = new NavbarManager()

document.addEventListener("DOMContentLoaded", () => {
  navbarManager.init()
})
