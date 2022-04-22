//
//  Repository.swift
//  ios
//
//  Created by Araz Abishov on 24/02/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import SDWebImageSwiftUI
import Foundation
import shared

struct Banner: View {
    let url: URL

    var body: some View {
        WebImage(url: url)
            .resizable()
            .transition(.fade(duration: 0.5))
            .scaledToFill()
            .frame(height: 194)
            .clipShape(Rectangle())
    }
}

struct Avatar: View {
    let url: URL

    var body: some View {
        WebImage(url: url)
            .resizable()
            .transition(.fade(duration: 0.5))
            .scaledToFit()
            .frame(width: 40, height: 40)
            .clipShape(RoundedRectangle(cornerRadius: 4))
    }
}

struct Header: View {
    let avatarUrl: URL?
    let author: String
    let name: String

    var body: some View {
        HStack {
            if let url = avatarUrl {
                Avatar(url: url)
            }

            Spacer().frame(width: 16)

            VStack(alignment: .leading) {
                Text(name).font(.headline).lineLimit(1)
                Text(author).font(.subheadline).lineLimit(1)
            }
        }
    }
}

struct Description: View {
    let description: String

    var body: some View {
        Text(description).font(.body).lineLimit(3)
    }
}

struct Tags: View {
    let primaryLanguage: LanguageModel?
    let topics: Array<TopicModel>?

    var body: some View {
        HStack {
            if let language = primaryLanguage {
                Text(language.name)
            }

            if let topics = topics {
                ForEach(topics, id: \.self) { topic in
                    Text(topic.name)
                }
            }
        }
    }
}

struct Contributors: View {
    let owner: OwnerModel
    let contributors: Array<ContributorModel>

    var body: some View {
        HStack {
            ForEach(contributors, id: \.self) { contributor in
                WebImage(url: URL(string: contributor.avatarUrl))
                    .resizable()
                    .transition(.fade(duration: 0.5))
                    .scaledToFit()
                    .frame(width: 28, height: 28)
                    .clipShape(RoundedRectangle(cornerRadius: 4))
            }
        }
    }
}

struct StarButton: View {
    let stars: Int32

    var body: some View {
        Button(action: {}) {
            HStack(alignment: .center) {
                Image(systemName: "star")
                    .padding(.trailing, 8)
                    .frame(width: 18, height: 18)
                Text(String(stars))
            }.frame(height: 24)
        }
        .buttonStyle(OutlineButton())
        .frame(maxWidth: .infinity, alignment: .trailing)
    }
}

struct Repository: View {
    let repository: RepositoryModel

    var body: some View {

        VStack(alignment: .leading) {
            if let bannerUrl = repository.bannerUrl {
                if let url = URL(string: bannerUrl) {
                    Banner(url: url)
                }
            }

            Header(
                avatarUrl: URL(string: repository.owner.avatarUrl),
                author: repository.owner.login,
                name: repository.name
            )

            Spacer()

            if let overview = repository.overview {
                Description(description: overview)
            }

            Spacer()

            Tags(
                primaryLanguage: repository.primaryLanguage,
                topics: repository.topics
            )

            if let mentionableUsers = repository.mentionableUsers {
                Contributors(
                    owner: repository.owner,
                    contributors: mentionableUsers.contributors
                )
            }

            StarButton(stars: repository.stars)
        }
//        .padding(.horizontal, 16)
        .frame()
        .background(Color.white)
//        .padding(.vertical, 8)
        .padding(.horizontal, 16)
    }
}

struct OutlineButton: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration
            .label
            .foregroundColor(configuration.isPressed ? .gray : .accentColor)
            .padding(.vertical, 8)
            .padding(.horizontal, 20)
            .background(
                RoundedRectangle(
                    cornerRadius: 4,
                    style: .continuous
                ).stroke(Color.gray)
            )
    }
}

