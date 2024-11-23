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
      source  = "timgchile/mongodbatlas"
      version = "1.3.8"
    }
  }
}

# An example resource that does nothing.
# Configure the MongoDB Atlas Provider for MongoDB Atlas for Government
provider "mongodbatlas" {
  public_key          = var.public_key
  private_key         = var.private_key
}
# Create the resources