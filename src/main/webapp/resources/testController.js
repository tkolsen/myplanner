app.controller("CoursesCtrl", function ($scope, $http, $q) {
    $scope.quantity = 5;
    $scope.text = 'Vis flere..';

    var courseList = $http.get("../rest/courses").success(function (response) {
        return response;
    });
    var username = $http.get("../rest/userName").success(function (response) {
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

    $scope.checkAllDeadlines = function(deadlineDetails){

    };

    $scope.checkOldestDeadlines = function(deadlineDetails){
        // TODO: implement me
    };

    $scope.generateSchedule = function(scheduleDetails){
        if(confirm("Dette vil overskrive din nåværende fremdriftsplan om den allerede eksisterer og generere en ny fremdriftsplan, og kan ikke tilbakestilles. Vil du fortsette?")){

            var courseName = $scope.selectedCourse.name;
            var courseObj;
            $scope.courses.forEach(function(course){
                if(course.name === courseName){
                    courseObj = course;
                }
            });

            var details = {
                "modules": courseObj.modules,
                "workHoursDaily": scheduleDetails.workHoursDaily,
                "startDate": scheduleDetails.startDate
            };
            console.log(details);
            $http({
                method: 'POST',
                url: '../rest/generateSchedule',
                data: details
            }).success(function(){
                console.log('heilt ok');
            });
        }
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