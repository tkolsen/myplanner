app.controller("CoursesCtrl", function ($scope, $http, $q) {
    $scope.quantity = 5;
    $scope.text = 'Vis flere..';

    var courseList = $http.get("/rest/courses").success(function (response) {
        return response;
    });
    var username = $http.get("/rest/userName").success(function (response) {
        return response;
    });
    $q.all([courseList, username]).then(function (arrayOfResult) {
        $scope.courses = arrayOfResult[0].data;
        $scope.selectedCourse = $scope.courses[0];
        $scope.username = arrayOfResult[1].data.user.name;
    });

    $scope.moduleClicked = function(){
        console.log("module clicked");
    };

    $scope.test = function(course, index){
        var module = course.modules[index];
        var items = module.items;
        var completionReq = new Array();
        items.forEach(function(item){
            if(item.completion_requirement != null){
                completionReq.push(item.completion_requirement);
            }
        });
        var count = completionReq.length;
        var countCompleted = 0;
        completionReq.forEach(function(req){
            if(req.completed){
                countCompleted++;
            }
        });

        if(count != 0){
            module.width = countCompleted / count * 100 + '%';
        }else if(count == 0 || count == null){
            module.width = 100+'%';
        }
    }
});