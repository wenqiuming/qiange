// Generated by CoffeeScript 1.6.3
/*(function() {
  $(function() {
   /!* var brick;
    brick = "<div class='brick small'><div class='delete'>&times;</div></div>";
    $(document).on("click", ".gridly .brick", function(event) {
      var $this, size;
      event.preventDefault();
      event.stopPropagation();
      $this = $(this);
      $this.toggleClass('small');
      $this.toggleClass('large');
      if ($this.hasClass('small')) {
        size = 140;
      }
      if ($this.hasClass('large')) {
        size = 300;
      }
      $this.data('width', size);
      $this.data('height', size);
      return $('.gridly').gridly('layout');
    });*!/
    return $('.gridly').gridly();
  });

}).call(this);*/
$(function () {
    // $('.gridly').gridly({
    //     // 'draggable':'on',
    //     callbacks: { reordering: reordering , reordered: reordered }
    // });
   // $('.gridly').gridly();
    $('.gridly').gridly({
        base: 60, // px
        gutter: 2, // px
        columns: 12
    });
});

var reordering = function(eles) {
};

var reordered = function(eles) {
  console.log(eles);
};
