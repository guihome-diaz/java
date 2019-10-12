(function(window, undefined) {
  'use strict';

  /*
  NOTE:
  ------
  PLACE HERE YOUR OWN JAVASCRIPT CODE IF NEEDED
  WE WILL RELEASE FUTURE UPDATES SO IN ORDER TO NOT OVERWRITE YOUR JAVASCRIPT CODE PLEASE CONSIDER WRITING YOUR SCRIPT HERE.  */

  $("#locales").change(function () {
    var selectedOption = $('#locales').val();
    if (selectedOption != '') {
      window.location.replace('?lang=' + selectedOption);
    }
  });

})(window);