(function ($) {

  "use strict";

  var initPreloader = function () {
    $(document).ready(function ($) {
      var Body = $('body');
      Body.addClass('preloader-site');
    });
    $(window).load(function () {
      $('.preloader-wrapper').fadeOut();
      $('body').removeClass('preloader-site');
    });
  }

  // background color when scroll 
  var initScrollNav = function () {
    var scroll = $(window).scrollTop();

    if (scroll >= 200) {
      $('.navbar.fixed-top').addClass("bg-white");
    } else {
      $('.navbar.fixed-top').removeClass("bg-white");
    }
  }

  $(window).scroll(function () {
    initScrollNav();
  });


  // init Chocolat light box
  var initChocolat = function () {
    Chocolat(document.querySelectorAll('.image-link'), {
      imageSize: 'contain',
      loop: true,
    })
  }


  var initProductQty = function () {

    $('.product-qty').each(function () {

      var $el_product = $(this);
      var quantity = 0;

      $el_product.find('.quantity-right-plus').click(function (e) {
        e.preventDefault();
        var quantity = parseInt($el_product.find('#quantity').val());
        $el_product.find('#quantity').val(quantity + 1);
      });

      $el_product.find('.quantity-left-minus').click(function (e) {
        e.preventDefault();
        var quantity = parseInt($el_product.find('#quantity').val());
        if (quantity > 0) {
          $el_product.find('#quantity').val(quantity - 1);
        }
      });

    });

  }

  // document ready
  $(document).ready(function () {

    var testimonial_swiper = new Swiper(".testimonial-swiper", {
      slidesPerView: 3,
      speed: 500,
      pagination: {
        el: ".swiper-pagination",
        clickable: true,
      },
      breakpoints: {
        320: {
          slidesPerView: 1,
          spaceBetween: 20
        },
        550: {
          slidesPerView: 2,
          spaceBetween: 30
        },
        1200: {
          slidesPerView: 3,
          spaceBetween: 40
        }
      }
    });

    // product single page
    var thumb_slider = new Swiper(".product-thumbnail-slider", {
      loop: true,
      slidesPerView: 3,
      autoplay: true,
      direction: "vertical",
      spaceBetween: 10,
    });

    var large_slider = new Swiper(".product-large-slider", {
      loop: true,
      slidesPerView: 1,
      autoplay: true,
      effect: 'fade',
      thumbs: {
        swiper: thumb_slider,
      },
    });


    var swiper = new Swiper(".swiper-carousel", {
      slidesPerView: 4,
      spaceBetween: 30,
      navigation: {
        nextEl: '.swiper-carousel .swiper-right',
        prevEl: '.swiper-carousel .swiper-left',
      },
      pagination: {
        el: ".swiper-pagination",
        clickable: true,
      },
      breakpoints: {
        300: {
          slidesPerView: 2,
        },
        768: {
          slidesPerView: 2,
          spaceBetween: 20,
        },
        1200: {
          slidesPerView: 3,
          spaceBetween: 30,
        },
      },
    });

    var swiper = new Swiper(".swiper-slideshow", {
      slidesPerView: 1,
      spaceBetween: 0,
      speed: 700,
      loop: true,
      navigation: {
        nextEl: '.swiper-slideshow .swiper-right',
        prevEl: '.swiper-slideshow .swiper-left',
      },
      pagination: {
        el: ".swiper-pagination",
        clickable: true,
      },
    });

    $(".youtube").colorbox({
      iframe: true,
      innerWidth: 960,
      innerHeight: 585
    });

    initPreloader();
    initChocolat();
    initProductQty();

  }); // End of a document

})(jQuery);

document.querySelectorAll('input[name="currentLevel"]').forEach(function (radio) {
    radio.addEventListener('change', updateTargetOptions);
});

document.querySelectorAll('input[name="targetScore"]').forEach(function (radio) {
    radio.addEventListener('change', function () {
        var currentLevel = document.querySelector('input[name="currentLevel"]:checked');
        if (currentLevel) {
            var targetScore = this.value;
            displayCourseInfo(currentLevel.value, targetScore);
        }
    });
});

function updateTargetOptions() {
    var currentLevel = document.querySelector('input[name="currentLevel"]:checked').value;
    document.querySelectorAll('input[name="targetScore"]').forEach(function (radio) {
        radio.disabled = false;
        radio.checked = false;

        if ((currentLevel === '350' && radio.value === '350') ||
            (currentLevel === '550' && (radio.value === '350' || radio.value === '550')) ||
            (currentLevel === '750' && (radio.value === '350' || radio.value === '550' || radio.value === '750'))) {
            radio.disabled = true;
        }
    });
}

function displayCourseInfo(level, score) {
    var courseRange = document.getElementById('courseRange');
    var courseDetails = document.getElementById('courseDetails');
    var courseInfo = document.getElementById('courseInfo');

    courseRange.innerHTML = '';
    courseDetails.innerHTML = '';

    var courses = [];
    var prices = [];

    if (level === '0') {
        if (score === '350') {
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 0 - 350');
            prices.push('Giá: 1500000');
        } else if (score === '550') {
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 0 - 350');
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 350 - 550');
            prices.push('Giá: 1500000', 'Giá: 2500000');
        } else if (score === '750') {
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 0 - 350');
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 350 - 550');
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 550 - 750');
            prices.push('Giá: 1500000', 'Giá: 2500000', 'Giá: 4000000');
        } else if (score === '750+') {
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 0 - 350');
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 350 - 550');
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 550 - 750');
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TRÊN 750+');
            prices.push('Giá: 1500000', 'Giá: 2500000', 'Giá: 4000000', 'Giá: 6000000');
        }
    } else if (level === '350') {
        if (score === '550') {
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 350 - 550');
            prices.push('Giá: 2500000');
        } else if (score === '750') {
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 350 - 550');
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 550 - 750');
            prices.push('Giá: 2500000', 'Giá: 4000000');
        } else if (score === '750+') {
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 350 - 550');
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 550 - 750');
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TRÊN 750+');
            prices.push('Giá: 2500000', 'Giá: 4000000', 'Giá: 6000000');
        }
    } else if (level === '550') {
        if (score === '750') {
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 550 - 750');
            prices.push('Giá: 4000000');
        } else if (score === '750+') {
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TỪ 550 - 750');
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TRÊN 750+');
            prices.push('Giá: 4000000', 'Giá: 6000000');
        }
    } else if (level === '750') {
        if (score === '750+') {
            courses.push('KHÓA HỌC LUYỆN THI TOEIC TRÊN 750+');
            prices.push('Giá: 6000000');
        }
    }

    courseRange.innerHTML = courses.map((course, index) => `${course}<br>${prices[index]}`).join('<br><br>');

    courseInfo.style.display = 'block';
}
function payNow() {
        // Xử lý logic thanh toán ở đây
        alert("Thanh toán thành công!");
}
function showPaymentInfo(paymentMethod) {
        document.getElementById('mbBankInfo').style.display = 'none';
        document.getElementById('momoInfo').style.display = 'none';

        if (paymentMethod === 'mbBank') {
            document.getElementById('mbBankInfo').style.display = 'block';
        } else if (paymentMethod === 'momo') {
            document.getElementById('momoInfo').style.display = 'block';
        }
    }

function applyDiscount() {
    const discountCode = document.getElementById('discountCode').value.trim(); // Lấy giá trị mã giảm giá và loại bỏ khoảng trắng ở đầu cuối
    let totalPrice = parseFloat(document.getElementById('initialPrice').innerText.replace(/,/g, ''));
    let discountedPrice;

    if (discountCode === 'KHOADAUTIEN20') {
        // Nếu mã giảm giá là KHOADAUTIEN20, giảm 20%
        discountedPrice = totalPrice - (totalPrice * 0.20);
        alert("Áp dụng mã giảm giá thành công !");
    } else {
        // Nếu mã giảm giá không đúng
        discountedPrice = totalPrice;
        alert("Mã giảm giá không đúng hoặc không tồn tại.");
    }

    document.getElementById('finalPrice').innerText = discountedPrice.toLocaleString('vi-VN', { minimumFractionDigits: 0, maximumFractionDigits: 2 });
}

function FreeDiscount() {
    // Xử lý logic thanh toán ở đây
    alert("Cám ơn bạn đã tin tưởng và lựa chọn Agent, ban giáo vụ Agent sẽ liên hệ tư vấn muộn nhất sau 24h ạ!");
  }

  document.addEventListener("DOMContentLoaded", function() {
          const descriptions = document.querySelectorAll(".card-text");
          descriptions.forEach(description => {
              const words = description.innerText.split(" ");
              if (words.length > 30) {
                  description.innerText = words.slice(0, 30).join(" ") + "...";
              }
          });
      });

      document.getElementById('exerciseForm').addEventListener('submit', function(event) {
              event.preventDefault();
              // Validate and process form data here
              document.getElementById('exerciseResult').innerHTML = 'Kết quả bài tập';
          });

          function validateForm() {
                      // Get all radio buttons
                      var radioButtons = document.querySelectorAll('input[type="radio"]');
                      var checked = false;

                      // Check if any radio button is checked
                      radioButtons.forEach(function(radioButton) {
                          if (radioButton.checked) {
                              checked = true;
                          }
                      });

                      // Return false if no radio button is checked, otherwise allow form submission
                      if (!checked) {
                          alert('Vui lòng chọn đáp án cho mỗi câu hỏi trước khi nộp !!');
                          return false;
                      }
                      return true;
                  }

                  function payNow(event) {
                      const confirmPayment = document.getElementById('confirmPayment');
                      const transactionImage = document.getElementById('transactionImage');

                      if (!transactionImage.files.length) {
                          alert("Vui lòng tải lên hình ảnh giao dịch thanh toán.");
                          return false; // Ngăn chặn gửi form
                      }

                      if (!confirmPayment.checked) {
                          alert("Vui lòng xác nhận thanh toán trước khi tiến hành thanh toán.");
                          return false; // Ngăn chặn gửi form
                      }

                      // Nếu đã xác nhận thanh toán và đã tải lên hình ảnh, cho phép gửi form
                      return true;
                  }