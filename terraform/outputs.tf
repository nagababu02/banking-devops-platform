output "aws_region" {
  description = "AWS region used for this environment."
  value       = "us-east-1"
}

output "vpc_id" {
  description = "VPC ID."
  value       = module.vpc.vpc_id
}

output "private_subnet_ids" {
  description = "Private subnet IDs used by EKS."
  value       = module.vpc.private_subnets
}

output "eks_cluster_name" {
  description = "EKS cluster name."
  value       = module.eks.cluster_name
}

output "eks_cluster_endpoint" {
  description = "EKS Kubernetes API endpoint."
  value       = module.eks.cluster_endpoint
}

output "ecr_repository_urls" {
  description = "ECR repository URLs by service."
  value       = { for name, repo in aws_ecr_repository.services : name => repo.repository_url }
}

output "jenkins_ecr_push_policy_arn" {
  description = "Attach this policy to the Jenkins agent role used for ECR image publishing."
  value       = aws_iam_policy.jenkins_ecr_push.arn
}
