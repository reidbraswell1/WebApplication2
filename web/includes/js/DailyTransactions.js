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
