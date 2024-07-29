import { Box, Grid, Image, Heading, Text } from "@chakra-ui/react";

const parts = [
  { src: "https://via.placeholder.com/200x200.png?text=CPU", title: "CPUs", description: "Power your build with the latest processors" },
  { src: "https://via.placeholder.com/200x200.png?text=GPU", title: "Graphics Cards", description: "Elevate your gaming experience" },
  { src: "https://via.placeholder.com/200x200.png?text=RAM", title: "Memory", description: "Boost your system's performance" },
  { src: "https://via.placeholder.com/200x200.png?text=SSD", title: "Storage", description: "Fast and reliable data storage solutions" },
];

const FeaturedParts = () => (
  <Box mt={8}>
    <Grid templateColumns="repeat(auto-fit, minmax(250px, 1fr))" gap={8}>
      {parts.map((part) => (
        <Box
          key={part.title}
          bg="gray.800"
          borderRadius="8px"
          boxShadow="lg"
          p={4}
          textAlign={"center"}
          _hover={{ transform: "translateY(-5px)", transition: "transform 0.3s ease" }}
        >
          <Image src={part.src} alt={part.title} mb={4} borderRadius="8px" />
          <Heading as="h3" color={"gray.100"} size="md">
            {part.title}
          </Heading>
          <Text color={"gray.300"} mt={2}>
            {part.description}
          </Text>
        </Box>
      ))}
    </Grid>
  </Box>
);

export default FeaturedParts;
