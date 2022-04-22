//
//  TrendingViewModel.swift
//  ios
//
//  Created by Araz Abishov on 08/02/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

enum EncryptionError: Error {
    case empty
    case short
}

class TrendingViewModel: ObservableObject {
    @Published var repositories: Array<RepositoryModel>? = nil
    private let service: GithubService = GithubServiceFactory().create()

    func fetch() {
        let searchQuery = SearchQuery(dateString: "2021-02-05", sort: .stars, order: .desc)

        service.search(
            query: searchQuery,
            repositoriesCount: 32,
            repositoryTopicsCount: 2,
            mentionableUsersCount: 4,
            ownerAvatarSize: 256,
            contributorAvatarSize: 128
        ) { (repositories: Array<RepositoryModel>?, error: Error?) in
            self.repositories = repositories
        }
    }
}
