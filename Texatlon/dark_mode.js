function into_dark() {
  const docs = document.getElementsByTagName("div");
  const tagClass = "dark-mode";

//   Body color
  document.body.classList.toggle(tagClass);

  //unusele ss
  // for (let index = 0; index < docs.length; index++) {
  //   const tr = docs.item(index);
  // }
//   Functions to other elements
    dark_nav_space()
    dark_icon_mode()
}

function dark_nav_space(){
   document.getElementById("nv").classList.toggle("dark-navbar");
   document.getElementById("nv").classList.toggle("navbar");

}

function dark_icon_mode(){
  const elem = document.getElementById("dark_icon").children[0];
  // console.log(elem)
  elem.classList.toggle("fa-sun");
  elem.classList.toggle("fa-moon");
}