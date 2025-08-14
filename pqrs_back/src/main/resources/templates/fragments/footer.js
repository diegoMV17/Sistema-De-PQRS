class FooterManager {
  render() {
    return `
      
  }

  init() {
    document.getElementById("footer-container").innerHTML = this.render()
  }
}

const footerManager = new FooterManager()

document.addEventListener("DOMContentLoaded", () => {
  footerManager.init()
})
