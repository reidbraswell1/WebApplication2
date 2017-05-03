function unhideInput() {
//alert("hello");
    var x=document.getElementById("selectAccount");
    var y=document.getElementById("newAccount");
    if(x.value=="Other") {
       y.setAttribute('type','number');
    }
    else {
       y.setAttribute('type','hidden');
    }
}//unhideInput//
function unhideInputText() {
//alert("hello");
    var x=document.getElementById("selectNote");
    var y=document.getElementById("newNote");
    if(x.value=="Other") {
       y.setAttribute('type','text');
       x.style.width="75px";  
    }
    else {
       y.setAttribute('type','hidden');
       x.style.width="";
    }
}//unhideInput//
function hideInput() {
//alert("hello");
    var y=document.getElementById("newAccount");
        y.setAttribute('type','hidden');
}//unhideInput//            
function limitLength(object) {
    if (object.value.length > 4) {
        object.value = object.value.slice(0,4); 
    }//if//
}//limitLength//
