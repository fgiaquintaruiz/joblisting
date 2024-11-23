variable "public_key" {
  description = "The public API key for MongoDB Atlas"
}
variable "private_key" {
  description = "The private API key for MongoDB Atlas"
}
variable "atlasprojectid" {
  description = "Atlas Project ID"
  default     = "5c98a80fc56c98ef210b8633"
}
variable "atlas_cluster_name" {
  description = "Atlas Project ID"
}
variable "atlas_dbuser" {
  description = "The db user for Atlas"
}
variable "atlas_dbpassword" {
  description = "The db user passwd for Atlas"
}
variable "atlas_dbname" {
  description = "The db name for Atlas"
}
