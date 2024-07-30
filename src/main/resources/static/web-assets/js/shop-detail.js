
/*<![CDATA[*/
document.addEventListener('DOMContentLoaded', function() {
    var labels = document.querySelectorAll('.product__details__option__color label');
    labels.forEach(function(label) {
        label.addEventListener('click', function(event) {
            // Xóa class 'selected' của tất cả các label trước khi chọn lại
            labels.forEach(function(l) {
                l.classList.remove('selected');
            });
            // Thêm class 'selected' vào label được chọn
            label.classList.add('selected');

            // Lấy giá trị id của màu đã chọn
            var input = label.querySelector('.product__details__option__color label input[type="radio"]');
            if (input && input.checked) {
                var colorId = input.value;
                console.log('Đã chọn màu có id: ' + colorId);
                changeImage(colorId,productId)
            }
        });
    });
});
/*]]>*/

/*Gọi api*/
const changeImage = async (colorId, productId) =>{
    try {
        const changeImg = await axios.get(`/api/images/${colorId}/${productId}`)
        console.log("Sự kiện đổi ảnh")
        console.log(changeImg.data)
        renderImages(changeImg.data)
    } catch (error) {
        console.log(error)
    }
};

//Render lại ảnh khi ấn màu
let imgListEl = document.querySelector(".img-list");
let imgListTabEl = document.querySelector(".img-list-tab")
const renderImages = images =>{
    let html1 = "";
    let html2 = "";
    images.forEach((img,index) =>{
        if (index===0){
            html1+=`
                           <li class="nav-item">
                                    <a class="nav-link active" data-toggle="tab" href="#tabs-${index}" role="tab">
                                        <div class="product__thumb__pic set-bg rounded-4" data-setbg="${img.imgUrl}" style="background-image: url('${img.imgUrl}');">
                                        </div>
                                    </a>
                                </li>
                        `
            html2+=`
                         <div class="tab-pane active" id="tabs-${index}" role="tabpanel">
                                    <div class="product__details__pic__item ">
                                        <img class="rounded-4" src="${img.imgUrl}" alt="">
                                    </div>
                                </div>
                        `
        }else {
            html1+=`
                          <li class="nav-item">
                                    <a class="nav-link" data-toggle="tab" href="#tabs-${index}" role="tab">
                                        <div class="product__thumb__pic set-bg rounded-4" data-setbg="${img.imgUrl}" style="background-image: url('${img.imgUrl}');">
                                        </div>
                                    </a>
                                </li>
                       `
            html2+=`
                         <div class="tab-pane" id="tabs-${index}" role="tabpanel">
                                    <div class="product__details__pic__item ">
                                        <img class="rounded-4" src="${img.imgUrl}" alt="">
                                    </div>
                                </div>
                        `
        }
    })
    imgListEl.innerHTML=html1;
    imgListTabEl.innerHTML=html2;
};