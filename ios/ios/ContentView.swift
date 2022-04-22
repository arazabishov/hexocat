import SwiftUI
import SDWebImageSwiftUI
import shared

struct ContentView: View {
    @ObservedObject var viewModel = TrendingViewModel()

    var body: some View {
        let repositories = viewModel.repositories ?? Array()

        NavigationView {
            ScrollView {
                LazyVStack(spacing: 16) {
                    ForEach(repositories, id: \.self) { repository in
                        Repository(repository: repository)
                    }
                }
            }
//            List(repositories, id: \.self) { repository in
//                Repository(repository: repository)
//            }
            .onAppear {
                viewModel.fetch()
            }
            .navigationTitle("Trending")
            .navigationBarItems(trailing: Button(action: {}) {
                ZStack {
                    Circle()
                        .fill(Color.gray)
                        .frame(width: 32, height: 32)
                    Text("AA").foregroundColor(Color.white)
                        .font(.caption)
                }
            })
            .background(Color.gray)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
