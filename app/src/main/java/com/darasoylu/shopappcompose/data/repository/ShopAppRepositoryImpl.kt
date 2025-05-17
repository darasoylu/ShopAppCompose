package com.darasoylu.shopappcompose.data.repository

import com.darasoylu.shopappcompose.data.model.CategoryModel
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.domain.repository.ShopAppRepository
import com.darasoylu.shopappcompose.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ShopAppRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : ShopAppRepository {

    private val productsCollectionPath = "products"
    private val categoriesCollectionPath = "categories"

    override fun getProducts(): Flow<Resource<List<ProductModel>>> = flow {
        emit(Resource.Loading)
        try {
            val documents = firebaseFirestore.collection(productsCollectionPath).get().await().documents
            val productList = mutableListOf<ProductModel>()

            for (document in documents) {
                document?.let {
                    val product = ProductModel(
                        document.id,
                        document.getString("name"),
                        document.getString("image"),
                        document.getDouble("price"),
                        document.getString("categoryId")
                    )
                    productList.add(product)

                }
            }

            if (productList.isNotEmpty()) {
                emit(Resource.Success(productList))
            }

        } catch (e: Exception) {
            emit(Resource.Error(e))
        }

    }

    override fun getCategories(): Flow<Resource<List<CategoryModel>>> = flow {
        emit(Resource.Loading)
        try {
            val documents = firebaseFirestore.collection(categoriesCollectionPath).get().await().documents
            val categoryList = mutableListOf<CategoryModel>()

            for (document in documents) {
                document?.let {
                    val category = CategoryModel(
                        document.id,
                        document.getString("name"),
                        document.getDouble("count"),
                        document.getDouble("sort")
                        )
                    categoryList.add(category)
                }
            }

            if (categoryList.isNotEmpty()) {
                emit(Resource.Success(categoryList))
            }

        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}
