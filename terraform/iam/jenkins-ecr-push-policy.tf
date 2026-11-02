data "aws_iam_policy_document" "jenkins_ecr_push" {
  statement {
    sid       = "AuthenticateToECR"
    actions   = ["ecr:GetAuthorizationToken"]
    resources = ["*"]
  }

  statement {
    sid = "PushProjectImages"

    actions = [
      "ecr:BatchCheckLayerAvailability",
      "ecr:CompleteLayerUpload",
      "ecr:InitiateLayerUpload",
      "ecr:PutImage",
      "ecr:UploadLayerPart"
    ]

    resources = [
      for repository in aws_ecr_repository.services : repository.arn
    ]
  }
}

resource "aws_iam_policy" "jenkins_ecr_push" {
  name        = "${local.project_name}-jenkins-ecr-push"
  description = "Least-privilege ECR push permissions for the banking Jenkins pipeline."
  policy      = data.aws_iam_policy_document.jenkins_ecr_push.json
}
