/*
 * Mustofa on 2/4/20
 * https://mustofa.id
 */
package id.mustofa.bayery.data.source.remote

sealed class NetworkState {
  object Loading : NetworkState()
  object Success : NetworkState()
  class Error(val message: String) : NetworkState()
}