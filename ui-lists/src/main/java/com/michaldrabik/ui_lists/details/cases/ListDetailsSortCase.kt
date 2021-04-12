package com.michaldrabik.ui_lists.details.cases

import com.michaldrabik.common.Mode
import com.michaldrabik.common.di.AppScope
import com.michaldrabik.common.extensions.nowUtcMillis
import com.michaldrabik.storage.database.AppDatabase
import com.michaldrabik.ui_model.CustomList
import com.michaldrabik.ui_model.SortOrderList
import com.michaldrabik.ui_repository.mappers.Mappers
import javax.inject.Inject

@AppScope
class ListDetailsSortCase @Inject constructor(
  private val database: AppDatabase,
  private val mappers: Mappers
) {

  suspend fun setSortOrder(listId: Long, sortOrder: SortOrderList): CustomList {
    database.customListsDao().updateSortByLocal(
      listId,
      sortOrder.slug,
      nowUtcMillis()
    )
    val list = database.customListsDao().getById(listId)!!
    return mappers.customList.fromDatabase(list)
  }

  suspend fun setSortTypes(listId: Long, types: List<Mode>): CustomList {
    check(types.isNotEmpty()) { "Can't be empty" }
    database.customListsDao().updateFilterTypeLocal(
      listId,
      types.joinToString(",") { it.type },
      nowUtcMillis()
    )
    val list = database.customListsDao().getById(listId)!!
    return mappers.customList.fromDatabase(list)
  }
}
