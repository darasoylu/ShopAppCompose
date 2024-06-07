package com.darasoylu.shopappcompose.repository

import android.util.Log
import com.darasoylu.shopappcompose.data.model.CategoryModel
import com.darasoylu.shopappcompose.data.model.ProductModel
import com.darasoylu.shopappcompose.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ShopAppRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : ShopAppRepository {

    override fun getProducts(collectionPath: String): Flow<Resource<List<ProductModel>>> = flow {
        emit(Resource.Loading)
        try {
            val documents = firebaseFirestore.collection(collectionPath).get().await().documents
            val productList = mutableListOf<ProductModel>()

            Log.i("abjhsdasd", documents.toString())

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
                    Log.i("abjhsdasd", productList.toString())

                }
            }
            Log.i("abjhsdasd2", productList.toString())

            if (productList.isNotEmpty()) {
                emit(Resource.Success(productList))
            }

        } catch (e: Exception) {
            emit(Resource.Error(e))
        }

    }

    override fun getCategories(collectionPath: String): Flow<Resource<List<CategoryModel>>> = flow {
        emit(Resource.Loading)
        try {
            val documents = firebaseFirestore.collection(collectionPath).get().await().documents
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
