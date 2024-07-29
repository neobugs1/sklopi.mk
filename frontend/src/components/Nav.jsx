import { Box, Flex, Link } from "@chakra-ui/react";

const Nav = () => (
  <Box bg="gray.700" p={2}>
    <Flex as="nav" justify="center">
      <Link href="/builder" color="gray.300" mx={2} fontWeight="bold">
        System Builder
      </Link>
      <Link href="/products" color="gray.300" mx={2} fontWeight="bold">
        Browse Products
      </Link>
      <Link href="/builds" color="gray.300" mx={2} fontWeight="bold">
        Completed Builds
      </Link>
      <Link href="/guide" color="gray.300" mx={2} fontWeight="bold">
        Build Guides
      </Link>
    </Flex>
  </Box>
);

export default Nav;
