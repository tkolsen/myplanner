app.controller("CoursesCtrl", function ($scope, $http, $q) {
    $scope.quantity = 5;
    $scope.text = 'Vis flere..';
    $scope.selectedCourse;

    var courseList = $http.get("/rest/courses").success(function (response) {
        return response;
    });
    var username = $http.get("/rest/userName").success(function (response) {
        return response;
    });
    $q.all([courseList, username]).then(function (arrayOfResult) {
        $scope.courses = arrayOfResult[0].data;
        $scope.username = arrayOfResult[1].data.user.name;
    });

    // TODO: This expands all. Find a way to expand only the clicked module
    $scope.showMore = function () {
        var size = 0;
        if ($scope.quantity == 5) {
            $scope.courses.forEach(function (course) {
                var modules = course.modules;
                modules.forEach(function (module) {
                    if (module.items_count > size) {
                        size = module.items_count;
                    }
                });
            });
            $scope.quantity = size;
            $scope.text = 'Vis færre..'
        } else {
            $scope.quantity = 5;
            $scope.text = 'Vis flere..'
        }
    };

    $scope.moduleClicked = function(){
        // TODO: Do something when user click a module
    };

    $scope.test = function(){
      alert($scope.selectedCourse);
    };
});