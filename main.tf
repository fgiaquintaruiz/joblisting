#The configuration for the `remote` backend.
terraform {
  backend "remote" {
    # The name of your Terraform Cloud organization.
    organization = "fgiaquintaruiz"

    # The name of the Terraform Cloud workspace to store Terraform state files in.
    workspaces {
      name = "joblisting"
    }
  }

  required_providers {
    mongodbatlas = {
      source = "mongodb/mongodbatlas"
      version = "1.4.6"
    }
  }
}

provider "mongodbatlas" {
  public_key = var.public_key
  private_key = var.private_key
}

resource "mongodbatlas_cluster" "db-cluster" {
  project_id              = var.atlasprojectid
  name                    = var.atlas_cluster_name

  # Provider Settings "block"
  provider_name = "TENANT" //free tier
  backing_provider_name = "AWS"
  provider_region_name = "EU_WEST_3" //free tier
  provider_instance_size_name = "M0" //free tier
}


resource "mongodbatlas_database_user" "dbuser" {
  username           = var.atlas_dbuser
  password           = var.atlas_dbpassword
  project_id         = var.atlasprojectid
  auth_database_name = "admin"

  roles {
    role_name     = "readWrite"
    database_name = var.atlas_dbname
  }

}

# resource "mongodbatlas_project_ip_access_list" "test" {
#   project_id = var.atlasprojectid
#   cidr_block = var.cidr
# }

output "db_cn_string" {
  value = mongodbatlas_cluster.db-cluster.connection_strings.0.standard_srv
}