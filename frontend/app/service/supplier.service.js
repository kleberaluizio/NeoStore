app.service('supplierService',['$http',function($http){
    const URL = "http://localhost:8080/supplier/";

    this.createSupplier = function(supplier){
        return $http.post(URL, supplier)
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
    
    function returnSupplierObject(supplier) {
        return {   
            name: supplier.name,
            email: supplier.email,
            description: supplier?.description,
            cnpj: supplier.cnpj
        };
    }
    
}]);
