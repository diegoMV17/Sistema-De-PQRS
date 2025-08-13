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
