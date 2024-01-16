app.service('supplierService',['$http',function($http){
    const URL = "http://localhost:8080/supplier/";
    const CONFIG = {
        headers: {
            'Content-Type': 'application/json',
        }
    };

    this.createSupplier = function(supplier){
        return $http.post(URL, supplier,CONFIG)
    };

    this.getAllSuppliers = function(){
        return $http.get(URL)
    };

    this.getPageSuppliers = function(itemsPerPage, page){
        let PAGE_URL = URL + `paginated?itemsPerPage=${itemsPerPage}&page=${page}`;
        return $http.get(PAGE_URL)
    };

    this.updateSupplier = function(supplier, id){
        return $http.put(URL+id, supplier)
    };

    this.deleteSupplier = function(id_supplier){
        return $http.delete(URL + id_supplier)
    };
    
}]);
